package io.protostuff.compiler.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.CompilerModule;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.generator.CompilerRegistry;
import io.protostuff.compiler.generator.ProtoCompiler;
import io.protostuff.compiler.model.Module;
import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.ParserException;
import io.protostuff.compiler.parser.ProtoContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtostuffCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffCompiler.class);

    private final Injector injector;

    public ProtostuffCompiler() {
        injector = Guice.createInjector(
                new ParserModule(Arrays.asList(Paths.get("."))),
                new CompilerModule());
    }

    public static void changeLogLevel(Level newLevel) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(newLevel);
        ctx.updateLoggers();
    }

    public static void main(String[] args) {

        Options options = new Options();
        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Print this message.")
                .build();
        Option includePath = Option.builder("I")
                .longOpt("proto_path")
                .argName("path")
                .numberOfArgs(1)
                .desc("Specify the directory in which to search for " +
                        "imports.  May be specified multiple times;" +
                        " directories will be searched in order.  If not" +
                        " given, the current working directory is used.")
                .build();
        Option outputOption = Option.builder("o")
                .longOpt("output")
                .argName("name")
                .numberOfArgs(1)
                .desc("Specify an output for compiler:\n" +
                        "* html - generate HTML documentation from given proto files;\n" +
                        "* proto - generate proto files, possibly apply additional transformations;")
                .build();
        Option verbose = Option.builder("v")
                .longOpt("verbose")
                .desc("Be verbose.")
                .build();
        Option debug = Option.builder("d")
                .longOpt("debug")
                .desc("Show debug information.")
                .build();
        options.addOption(help);
        options.addOption(includePath);
        options.addOption(debug);
        options.addOption(verbose);
        options.addOption(outputOption);

        List<String> protoFiles;
        String output;
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                // automatically generate the help statement
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("protostuff-compiler [options] proto_files", options);
                return;
            }
            if (cmd.hasOption("debug")) {
                changeLogLevel(Level.DEBUG);
            } else if (cmd.hasOption("verbose")) {
                changeLogLevel(Level.INFO);
            }
            output = cmd.getOptionValue("output");
            protoFiles = cmd.getArgList();
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            return;
        }

        LOGGER.info("Version={}", ProtostuffCompiler.class.getPackage().getImplementationVersion());
        ModuleConfiguration configuration = ModuleConfiguration.newBuilder()
                .name("main")
                .protoFiles(protoFiles)
                .output(output)
                .build();

        if (configuration.getProtoFiles().isEmpty()) {
            LOGGER.error("Missing input file.");
            return;
        }
        if (configuration.getOutput() == null) {
            LOGGER.error("Missing output directives.");
            return;
        }
        try {
            ProtostuffCompiler compiler = new ProtostuffCompiler();
            compiler.compile(configuration);
        } catch (ParserException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void compile(ModuleConfiguration configuration) {
        Importer importer = injector.getInstance(Importer.class);
        CompilerRegistry registry = injector.getInstance(CompilerRegistry.class);
        ProtoCompiler compiler = registry.findCompiler(configuration.getOutput());
        if (compiler == null) {
            LOGGER.error("Unknown output: {}", configuration.getOutput());
            return;
        }
        Map<String, Proto> importedFiles = new HashMap<>();
        for (String path : configuration.getProtoFiles()) {
            LOGGER.info("Parse {}", path);
            ProtoContext context = importer.importFile(path);
            Proto proto = context.getProto();
            importedFiles.put(path, proto);
        }
        Module module = new Module();
        module.setName(configuration.getName());
        for (Map.Entry<String, Proto> entry : importedFiles.entrySet()) {
            module.addProto(entry.getValue());
        }
        compiler.compile(module);
    }

}

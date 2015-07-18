package io.protostuff.compiler.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.CompilerModule;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.generator.CompilerRegistry;
import io.protostuff.compiler.generator.GeneratorException;
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

    public static final String __VERSION = ProtostuffCompiler.class.getPackage().getImplementationVersion();
    public static final String TEMPLATE = "template";
    public static final String OUTPUT = "output";
    public static final String DEBUG = "debug";
    public static final String HELP = "help";
    public static final String PROTO_PATH = "proto_path";
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
                .longOpt(HELP)
                .desc("Print this message.")
                .build();
        Option includePath = Option.builder("I")
                .longOpt(PROTO_PATH)
                .argName("path")
                .numberOfArgs(1)
                .desc("Specify the directory in which to search for " +
                        "imports.  May be specified multiple times;" +
                        " directories will be searched in order.  If not" +
                        " given, the current working directory is used.")
                .build();
        Option templateOption = Option.builder("t")
                .longOpt(TEMPLATE)
                .argName("name")
                .numberOfArgs(1)
                .desc("Specify an template template for compiler:\n" +
                        "* html - generate HTML documentation from given proto files;\n" +
                        "* proto - generate proto files, possibly apply additional transformations;")
                .build();
        Option outputOption = Option.builder("o")
                .longOpt(OUTPUT)
                .argName("name")
                .numberOfArgs(1)
                .desc("Specify an template directory for saving generated files.")
                .build();
        Option debug = Option.builder("d")
                .longOpt(DEBUG)
                .desc("Show debug information.")
                .build();
        options.addOption(help);
        options.addOption(includePath);
        options.addOption(debug);
        options.addOption(outputOption);
        options.addOption(templateOption);

        List<String> protoFiles;
        String template;
        String output;
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(HELP)) {
                // automatically generate the help statement
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("protostuff-compiler [options] proto_files", options);
                return;
            }
            if (cmd.hasOption(DEBUG)) {
                changeLogLevel(Level.DEBUG);
            }
            if (cmd.hasOption(TEMPLATE)) {
                template = cmd.getOptionValue(TEMPLATE);
            } else {
                LOGGER.error("Template is not set.");
                return;
            }
            if (cmd.hasOption(OUTPUT)) {
                output = cmd.getOptionValue(OUTPUT);
            } else {
                LOGGER.error("Output directory is not set.");
                return;
            }
            protoFiles = cmd.getArgList();
        } catch (ParseException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Could not parse command", e);
            } else {
                LOGGER.error(e.getMessage());
            }
            return;
        }

        LOGGER.info("Version={}", __VERSION);
        ModuleConfiguration configuration = ModuleConfiguration.newBuilder()
                .name("main")
                .protoFiles(protoFiles)
                .template(template)
                .output(output)
                .build();

        if (configuration.getProtoFiles().isEmpty()) {
            LOGGER.error("Missing input file.");
            return;
        }
        if (configuration.getTemplate() == null) {
            LOGGER.error("Missing template directives.");
            return;
        }
        try {
            ProtostuffCompiler compiler = new ProtostuffCompiler();
            compiler.compile(configuration);
        } catch (GeneratorException | ParserException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Compilation error", e);
            } else {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void compile(ModuleConfiguration configuration) {
        Importer importer = injector.getInstance(Importer.class);
        CompilerRegistry registry = injector.getInstance(CompilerRegistry.class);
        ProtoCompiler compiler = registry.findCompiler(configuration.getTemplate());
        if (compiler == null) {
            LOGGER.error("Unknown template: {}", configuration.getTemplate());
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
        module.setOutput(configuration.getOutput());
        for (Map.Entry<String, Proto> entry : importedFiles.entrySet()) {
            module.addProto(entry.getValue());
        }
        compiler.compile(module);
    }

}

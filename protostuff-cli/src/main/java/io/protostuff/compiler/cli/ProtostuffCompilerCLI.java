package io.protostuff.compiler.cli;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.generator.CompilerModule;
import io.protostuff.generator.CompilerRegistry;
import io.protostuff.generator.ProtostuffCompiler;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtostuffCompilerCLI extends ProtostuffCompiler {

    public static final String __VERSION = ProtostuffCompilerCLI.class.getPackage().getImplementationVersion();
    public static final String GENERATOR = "generator";
    public static final String TEMPLATE = "template";
    public static final String EXTENSIONS = "extensions";
    public static final String OUTPUT = "output";
    public static final String DEBUG = "debug";
    public static final String VERSION = "version";
    public static final String HELP = "help";
    public static final String PROTO_PATH = "proto_path";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffCompilerCLI.class);

    public static void main(String[] args) {
        new ProtostuffCompilerCLI().run(args);
    }

    private static void changeLogLevel(Level newLevel) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(newLevel);
        ctx.updateLoggers();
    }

    void run(String[] args) {
        CompilerRegistry registry = injector.getInstance(CompilerRegistry.class);
        Options options = new Options();
        options.addOption(Option.builder("h")
                .longOpt(HELP)
                .desc("Print this message.")
                .build());
        options.addOption(Option.builder("I")
                .longOpt(PROTO_PATH)
                .argName("dir")
                .numberOfArgs(1)
                .desc("Specify the directory in which to search for " +
                        "imports.  May be specified multiple times;" +
                        " directories will be searched in order.  If not" +
                        " given, the current working directory is used.")
                .build());
        options.addOption(Option.builder("d")
                .longOpt(DEBUG)
                .desc("Show debug information.")
                .build());
        options.addOption(Option.builder("v")
                .longOpt(VERSION)
                .desc("Show version.")
                .build());
        options.addOption(Option.builder("o")
                .longOpt(OUTPUT)
                .argName("dir")
                .numberOfArgs(1)
                .desc("Specify a directory for saving generated files.")
                .build());
        options.addOption(Option.builder("g")
                .longOpt(GENERATOR)
                .argName("name")
                .numberOfArgs(1)
                .desc("Specify compiler: " + Joiner.on('|').join(registry.availableCompilers()))
                .build());
        options.addOption(Option.builder("t")
                .longOpt(TEMPLATE)
                .argName("file")
                .numberOfArgs(1)
                .desc("[st4] Specify a template for st4 compiler")
                .build());
        options.addOption(Option.builder("e")
                .longOpt(EXTENSIONS)
                .argName("class")
                .numberOfArgs(1)
                .desc("[st4] Specify full class name of an extensions provider for st4 compiler")
                .build());
        CommandLineParser parser = new DefaultParser();
        ModuleConfiguration configuration = new ModuleConfiguration();
        configuration.setName("main");
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(HELP)) {
                printHelp(options);
                return;
            }
            if (cmd.hasOption(VERSION)) {
                Package aPackage = ProtostuffCompilerCLI.class.getPackage();
                String version = aPackage.getImplementationVersion();
                System.out.println(version);
                return;
            }
            if (cmd.hasOption(DEBUG)) {
                changeLogLevel(Level.DEBUG);
            }
            if (cmd.hasOption(GENERATOR)) {
                String generator = cmd.getOptionValue(GENERATOR);
                configuration.setGenerator(generator);
            } else {
                LOGGER.error("Generator is not set.");
                return;
            }
            if (cmd.hasOption(OUTPUT)) {
                String output = cmd.getOptionValue(OUTPUT);
                configuration.setOutput(output);
            } else {
                LOGGER.error("Output directory is not set.");
                return;
            }
            Map<String, Object> moduleOptions = new HashMap<String, Object>();
            if (cmd.hasOption(TEMPLATE)) {
                List<String> templates = Collections.singletonList(cmd.getOptionValue(TEMPLATE));
                moduleOptions.put(CompilerModule.TEMPLATES_OPTION, templates);
            }
            if (cmd.hasOption(EXTENSIONS)) {
                moduleOptions.put(CompilerModule.EXTENSIONS_OPTION, cmd.getOptionValue(EXTENSIONS));
            }
            configuration.setOptions(moduleOptions);
            List<File> includePaths = new ArrayList<File>();
            if (cmd.hasOption(PROTO_PATH)) {
                String[] paths = cmd.getOptionValues(PROTO_PATH);
                for (String path : paths) {
                    includePaths.add(new File(path));
                }
            }
            if (includePaths.isEmpty()) {
                includePaths.add(new File("."));
            }
            configuration.setIncludePaths(includePaths);
            List<String> protoFiles = cmd.getArgList();
            configuration.setProtoFiles(protoFiles);
        } catch (ParseException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Could not parse command", e);
            } else {
                LOGGER.error(e.getMessage());
            }
            return;
        }

        LOGGER.info("Version={}", __VERSION);

        if (configuration.getProtoFiles().isEmpty()) {
            LOGGER.error("Missing input file.");
            return;
        }
        if (configuration.getGenerator() == null) {
            LOGGER.error("Missing generator directives.");
            return;
        }
        try {
            compile(configuration);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Compilation error", e);
            } else {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void printHelp(Options options) {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(79);
        final Map<String, Integer> order = ImmutableMap.<String, Integer>builder()
                .put(HELP, 1)
                .put(PROTO_PATH, 2)
                .put(GENERATOR, 3)
                .put(OUTPUT, 4)
                .put(TEMPLATE, 5)
                .put(EXTENSIONS, 6)
                .put(DEBUG, 100)
                .build();
        formatter.setOptionComparator(new Comparator<Option>() {
            @Override
            public int compare(Option o1, Option o2) {
                return compare(
                        MoreObjects.firstNonNull(order.get(o1.getLongOpt()), 99),
                        MoreObjects.firstNonNull(order.get(o2.getLongOpt()), 99));
            }

            private int compare(int x, int y) {
                return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });
        formatter.printHelp("protostuff-compiler [options] proto_files", options);
    }

}

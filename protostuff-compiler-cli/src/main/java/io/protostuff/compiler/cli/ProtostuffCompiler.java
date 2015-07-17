package io.protostuff.compiler.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.CompilerModule;
import io.protostuff.compiler.ParserModule;
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

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtostuffCompiler {

    private final Injector injector;

    public ProtostuffCompiler() {
        injector = Guice.createInjector(
                new ParserModule(),
                new CompilerModule("io/protostuff/compiler/protodoc/tree.stg"));
    }

    public void compile(ModuleConfiguration configuration) {
        Importer importer = injector.getInstance(Importer.class);
        Map<Path, Proto> importedFiles = new HashMap<>();
        for (String path : configuration.getProtoFiles()) {
            ProtoContext context = importer.importFile(path);
            Proto proto = context.getProto();
            ProtoCompiler compiler = injector.getInstance(ProtoCompiler.class);
            compiler.compile(new Module(proto));
        }
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
        options.addOption(help);
        options.addOption(includePath);

        List<String> protoFiles;
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                // automatically generate the help statement
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "protostuff-compiler [OPTION] PROTO_FILES", options );
                return;
            }
            protoFiles = cmd.getArgList();
        } catch (ParseException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        ModuleConfiguration configuration = ModuleConfiguration.newBuilder()
                .protoFiles(protoFiles)
                .build();

        try {
            ProtostuffCompiler compiler = new ProtostuffCompiler();
            compiler.compile(configuration);
        } catch (ParserException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

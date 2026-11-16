import com.sun.source.tree.*;
import com.sun.source.util.*;
import javax.lang.model.element.Modifier;
import javax.tools.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** Parse declarations without resolving dependencies or executing application code. */
class ExtractApi {
    public static void main(String[] args) throws Exception {
        var compiler = ToolProvider.getSystemJavaCompiler();
        try (var manager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8)) {
            var files = manager.getJavaFileObjectsFromStrings(Files.readAllLines(Path.of(args[0])));
            var task = (JavacTask) compiler.getTask(null, manager, diagnostic -> {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR)
                    throw new IllegalStateException(diagnostic.toString());
            }, List.of("-proc:none"), null, files);
            for (var unit : task.parse()) {
                var out = new StringBuilder();
                new TreeScanner<Void, Boolean>() {
                    @Override public Void visitClass(ClassTree tree, Boolean parentInterface) {
                        var flags = tree.getModifiers().getFlags();
                        if (!(flags.contains(Modifier.PUBLIC) || Boolean.TRUE.equals(parentInterface))) return null;
                        out.append("\n### ").append(tree.getSimpleName()).append("\n\n```java\n");
                        boolean iface = tree.getKind() == Tree.Kind.INTERFACE || tree.getKind() == Tree.Kind.ANNOTATION_TYPE;
                        for (var member : tree.getMembers()) {
                            if (member instanceof MethodTree method) {
                                var modifiers = method.getModifiers().getFlags();
                                if (modifiers.contains(Modifier.PRIVATE) || !(iface || modifiers.contains(Modifier.PUBLIC) || modifiers.contains(Modifier.PROTECTED))) continue;
                                out.append(method.getModifiers().toString().replaceAll("\\s+", " "));
                                if (!method.getTypeParameters().isEmpty()) out.append('<').append(String.join(", ", method.getTypeParameters().stream().map(Object::toString).toList())).append("> ");
                                if (method.getReturnType() != null) out.append(method.getReturnType()).append(' ');
                                out.append(method.getName().contentEquals("<init>") ? tree.getSimpleName() : method.getName());
                                out.append('(').append(String.join(", ", method.getParameters().stream().map(Object::toString).toList())).append(')');
                                if (!method.getThrows().isEmpty()) out.append(" throws ").append(String.join(", ", method.getThrows().stream().map(Object::toString).toList()));
                                if (method.getDefaultValue() != null) out.append(" default ").append(method.getDefaultValue());
                                out.append(";\n");
                            } else if (member instanceof VariableTree field && (iface || field.getModifiers().getFlags().contains(Modifier.PUBLIC))) {
                                out.append(field.getModifiers().toString().replaceAll("\\s+", " ")).append(field.getType()).append(' ').append(field.getName()).append(";\n");
                            }
                        }
                        out.append("```\n");
                        for (var member : tree.getMembers()) if (member instanceof ClassTree nested) scan(nested, iface);
                        return null;
                    }
                }.scan(unit, false);
                String path = Path.of(unit.getSourceFile().toUri()).toAbsolutePath().toString();
                System.out.println(Base64.getEncoder().encodeToString(path.getBytes(StandardCharsets.UTF_8)) + "\t" + Base64.getEncoder().encodeToString(out.toString().getBytes(StandardCharsets.UTF_8)));
            }
        }
    }
}

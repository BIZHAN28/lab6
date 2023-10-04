import java.util.Stack;

public class ScriptStack {
    private Stack<String> stack;
    public ScriptStack(){
        this.stack = new Stack<>();
    }
    public void push(String script){
        this.stack.push(script);
    }
    public void pop(){
        this.stack.pop();
    }

    public boolean contains(String script){
        return this.stack.contains(script);
    }
    public int size(){
        return this.stack.size();
    }

}

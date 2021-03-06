package org.idey.algo.datastructure.list.undoredo;

import org.idey.algo.oops.UndoRedo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UndoRedoList<T> {
    private final Logger logger = Logger.getLogger(UndoRedo.class.getName());
    private List<T> list;
    private Stack<AbstractListCommand<T>> undoStack;
    private Stack<AbstractListCommand<T>> redoStack;
    private AbstractListCommand<T> currentCommand;

    public UndoRedoList() {
        list = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        currentCommand = null;
        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.ALL);
    }

    private void execute(int index, T object, Operation op){
        AbstractListCommand<T> prevCommand = currentCommand;
        if(op == Operation.ADD){
            currentCommand = new AddCommand<>(index,object);
        }else if (op == Operation.UPDATE){
            currentCommand = new UpdateCommand<>(index,object);
        }else if(op == Operation.DELETE){
            currentCommand = new DeleteCommand<>(index);
        }
        undoStack.push(prevCommand);
        currentCommand.redo(list);
        logger.info(String.format("Undo Stack %s", undoStack.toString()));
        logger.info(String.format("Redo Stack %s", redoStack.toString()));

    }

    public void add(T object){
        add(list.size(),object);
    }

    public void add(int index, T object){
        execute(index,object, Operation.ADD);
    }

    public void remove(T object){
        int index = list.indexOf(object);
        remove(index);
    }

    public void remove(int index){
        execute(index,null,Operation.DELETE);
    }


    public void update(int index, T object){
        execute(index,object,Operation.UPDATE);
    }

    public void undo(){
        if(!canUndo()){
            throw new IllegalStateException("Can not undo!!");
        }
        currentCommand.undo(list);
        redoStack.push(currentCommand);
        currentCommand = undoStack.pop();
        logger.info(String.format("Undo Stack %s", undoStack.toString()));
        logger.info(String.format("Redo Stack %s", redoStack.toString()));
    }

    public void redo(){
        if(!canRedo()){
            throw new IllegalStateException("Can not redo!!");
        }
        AbstractListCommand<T> prevCommand = currentCommand;
        undoStack.push(prevCommand);
        currentCommand = redoStack.pop();
        currentCommand.redo(list);
        logger.info(String.format("Undo Stack %s", undoStack.toString()));
        logger.info(String.format("Redo Stack %s", redoStack.toString()));
    }

    public boolean canUndo(){
        return !undoStack.isEmpty();
    }

    public boolean canRedo(){
        return !redoStack.isEmpty();
    }

    @Override
    public String toString() {
        return "UndoRedoList{" +
                "list=" + list +
                '}';
    }

    private enum Operation{
        ADD, UPDATE, DELETE
    }

    public static void main(String[] args) {
        UndoRedoList<Integer> list = new UndoRedoList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove((Integer)2);
        while (list.canUndo()){
            list.undo();
        }
        System.out.println(list);
        while (list.canRedo()){
            list.redo();
        }
        System.out.println(list);

    }

}

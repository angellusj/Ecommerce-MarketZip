package model.dao.test;

import java.util.ArrayList;
import java.util.List;

public class TesteDaoComposite implements TesteDaoComponent{
     List<TesteDaoComponent> testes;

    public TesteDaoComposite(TesteDaoComponent... args){
        //testes = List.of(args);
        testes = new ArrayList<>(List.of(args));
    }


    @Override
    public boolean teste() {
        for(TesteDaoComponent t : testes){
            if(!t.teste())
                return false;
        }
        return true;
    }
}

package com.example.ultim.todolist;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ultim on 09.02.2017.
 */

public class Adapter {
    private ArrayList<HashMap<String, String>> examList;

    public ArrayList<HashMap<String, String>> getTodoMap(){
        examList = new ArrayList<>();
        examList.clear();
        for (int i = 0; i < 15; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", "Загловок");
            hashMap.put("text", "Дубовый листок оторвался от ветки родимой\n" +
                    "И в степь укатился, жестокою бурей гонимый;\n" +
                    "Засох и увял он от холода, зноя и горя\n" +
                    "И вот, наконец, докатился до Черного моря,\n" +
                    "У Черного моря чинара стоит молодая;\n" +
                    "С ней шепчется ветер, зеленые ветви лаская;\n" +
                    "На ветвях зеленых качаются райские птицы;\n" +
                    "Поют они песни про славу морской царь-девицы,\n" +
                    "И странник прижался у корня чинары высокой;\n" +
                    "Приюта на время он молит с тоскою глубокой,\n" +
                    "И так говорит он: «Я бедный листочек дубовый,\n" +
                    "До срока созрел я и вырос в отчизне суровой.");
            hashMap.put("priority", "max");
            examList.add(hashMap);
        }
        return examList;
    }
}

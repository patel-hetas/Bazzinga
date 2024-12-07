package com.bazzinga.ciphernet;

import java.util.ArrayList;

class History {
    private ArrayList<String> history;
    private int currIndex;

    History() {
        history = new ArrayList<>();
        currIndex = -1;
    }

    void addHistory(String string) {
    	
        if (currIndex == history.size() - 1) {
            history.add(string);
        } else {
            for (int ind = currIndex + 1; ind < history.size(); ind++) {
                history.remove(ind);
            }
            history.add(string);
        }
        currIndex++;
    }

    String forward() {
        if (currIndex < history.size() - 1) {
            return history.get(currIndex++);
        }
        return null;
    }

    String backward() {
        if (currIndex > 0) {
            return history.get(currIndex--);
        }
        return null;
    }

    void printHistory() {
        for (String entry : history) {
            System.out.println(entry);
        }
    }
}

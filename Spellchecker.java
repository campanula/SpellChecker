import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Spellchecker {

    public static sNode root;
    public static int total = 0;

    // 2A: The outline
    public Spellchecker() {
        //sets up empty tree
        root = new sNode();
    }

    // 2A: The outline
    public static class sNode {
        //Creating the hashmap for the trie
        public HashMap<Character, sNode> childNodes = new HashMap<>();
        public boolean end;

        // Node constructor for empty tree
        public sNode() {

        }

        // Node constructor for adding to tree
        public sNode(char ch) {

        }

        // setter for end
        public void setEnd(boolean end) {
            this.end = end;
        }

        // getter for end
        public boolean getEnd() {
            return end;
        }

        // setter for childNodes
        public void setChildNodes(HashMap<Character, sNode> childNodes) {
            this.childNodes = childNodes;
        }

        // getter for childNodes
        public HashMap<Character, sNode> getChildNodes() {
            return childNodes;
        }

    }

    // 2B: Adding words to the trie
    public static void addWord(String word) {
        // Call hashmap
        HashMap<Character, sNode> childNodes = root.getChildNodes();
        sNode n = null;

        for (int i = 0; i < word.length(); i++) {
            //if word is not in the hashmap, add it
            if (!childNodes.containsKey(word.charAt(i))) {
                n = new sNode(word.charAt(i));
                childNodes.put(word.charAt(i), n);

                //if word is in the hashmap, return it
            } else if (childNodes.containsKey(word.charAt(i))) {
                n = childNodes.get(word.charAt(i));
            }

            // when the childNode is the end of a word, set end as true
            childNodes = n.getChildNodes();
            int endOfWord = word.length() - 1;
            if (i == endOfWord) {
                n.setEnd(true);
            }
        }
    }

    // 2C: Querying the trie
    public static sNode searchWord(String word) {
        // Call hashmap
        HashMap<Character, sNode> childNodes = root.getChildNodes();
        sNode n = null;

        for (int i = 0; i < word.length(); i++) {
            // if the hashmap does not contain the word, do nothing
            if (!childNodes.containsKey(word.charAt(i))) {
                n = null;
                break;
                // if the hashmap does contain the word, return the word
            } else if (childNodes.containsKey(word.charAt(i))) {
                n = childNodes.get(word.charAt(i));
                childNodes = n.getChildNodes();
            }
        }
        return n;
    }

    // 2C: Querying the trie
    public static boolean containsWord(String word) {
        // search for the word
        sNode n = searchWord(word);

        // if the word exists return the word
        // if the word does not exist, do nothing
        return n != null && n.getEnd();
    }

    // 2D: How many words?
    public static int countWords() {
        System.out.println("Words in the dictionary: " + total);
        return total;
    }

    // 2E: Load the dictionary
    //adds every word in the named file to the tree
    public void addFile(String filename) throws IOException {
        String line = null;
        // load the reader
        BufferedReader br = new BufferedReader(new FileReader(filename));
        // for every word in the dictionary, add it to the trie and increase the counter by one
        while ((line = br.readLine()) != null) {
            Spellchecker.addWord(line);
            total = total + 1;
        }
        // close the reader
        br.close();
    }

    // 2F: Spellcheck a file
    // checks if the words in the sample file exist in the dictionary
    public static int checkFile(String filename) throws IOException {
        String bline = null;
        int totals = 0;
        // load the reader
        BufferedReader br2 = new BufferedReader(new FileReader(filename));
        // for every word in the file, turn it to lower case, remove punctuation and split at every space
        while ((bline = br2.readLine()) != null) {
            String cline = bline.replaceAll("[^a-zA-Z0-9]", " ");
            String line = cline.toLowerCase().replaceAll("( )+", " ");
            String[] parts = line.split(" ");

            // for every word in the file, if it does not exist in the dictionary, print it to the terminal and increase the counter by one
            for (int i = 0; i < parts.length; i++) {
                if (!containsWord(parts[i])) {
                    if (parts[i].trim().length() > 0) {
                        System.out.println(parts[i]);
                        totals = totals + 1;
                    }
                }
            }
        }
        System.out.println("----------");
        System.out.println("Number of misspelled words: " + totals);
        br2.close();
        return totals;
    }


    // 2G: Putting everything together
    public static void main(String[] args) throws IOException {
        // create a new spellchecker
        Spellchecker s = new Spellchecker();
        System.out.println(
                "//===================================================================================================\\\\\n" +
                        "// xxxx                                       SPELLCHECKER                                       xxxx \\\\\n" +
                        "//====================================================================================================\\\\");
        // compare the file to the dictionary and count the words in the dictionary
        s.addFile("src/dictionary.txt");
        s.checkFile("src/sample.txt");
        s.countWords();
    }
}
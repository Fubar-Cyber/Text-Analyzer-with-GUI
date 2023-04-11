package TextAnalyzer;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.jsoup.*;
import org.jsoup.nodes.*;

/**
 * This is my word occurrences SLDC project for CEN 3024.  
 * The program returns the top 20 words in my word occurrence.
 * A Graphic User Interface is used to request url and scan document 
 * where program will count word repetition and make list of top
 * twenty words with corresponding occurrences.
 *
 * @version 0.4.0
 * @since 2023-04-10
 * @author Jensy Fernandez
 */
@SuppressWarnings("serial")
public class Main2 extends JFrame implements ActionListener {

    /**
     * Swing to allow editing
     */
	private JTextField textField;
    private JTextArea textArea;

    public Main2() {
        super("UI Design Assignment for word occurrences");

        /**
         *  Add yellow background color to GUI
         */
        
        getContentPane().setBackground(Color.YELLOW);

        // Create a panel and add it to the content pane
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        getContentPane().add(panel);

        /**
         *  Set the layout of the panel to vertical box layout
         */
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        /**
         *  Create a label and set its features
         */
        
        JLabel label = new JLabel("Please enter URL for website you wish to scan:");
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        label.setFont(labelFont);
        label.setForeground(Color.BLUE);

        /**
         *  Create a text field and a text area, and set their features
         */
        
        textField = new JTextField(10);
        textArea = new JTextArea(20, 10);
        textArea.setEditable(false);
        textArea.setForeground(Color.RED);
        Font font = new Font("Arial", Font.BOLD, 15);
        textArea.setFont(font);

        /**
         *  Create a scroll pane and add the text area to it
         */
        
        JScrollPane scrollPane = new JScrollPane(textArea);

        /**
         *  Create a button and set its features
         */
        
        JButton button = new JButton("Capture first 20 words");
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        button.setFont(buttonFont);
        button.setForeground(Color.BLUE);
        button.addActionListener(this);

        /**
         *  Set the layout of the content pane to flow layout
         */
        
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));

        /**
         *  Add the button to the content pane
         */
        
        getContentPane().add(button);

        /**
         *  Add the label, text field, button, and scroll pane to the panel
         */
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        panel.add(scrollPane);

        /**
         *  Add the panel to the frame
         */ 
        this.add(panel, BorderLayout.CENTER);

        /**
         * Set the size of the frame
         */
        this.setPreferredSize(new Dimension(400, 500));

        /**
         *  Set the default close operation for the frame
         */        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pack the frame and make it visible      
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        
    	/**
    	 *  Get the text from the text field
    	 */
        String url = textField.getText();

        /**
         *  If the text field is empty, display a message and return
         */
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter URL https://www.gutenberg.org/files/1065/1065-h/1065-h.htm or other in same format");
            return;
        }

        try {
            /**
             *  Connect to the URL and get the page content
             */ 
            Document page = Jsoup.connect(url).get();

            /**
             *  Get the text from the first chapter DIV
             */
            Element div = page.select("div.chapter").first();
            String divText = div.text();

            /**
             *  Create a scanner to read the text, and a map to store the word counts
             */
            Scanner sc = new Scanner(divText);
            Map<String, Integer> map = new HashMap<String, Integer>();

            /**
             *  Loop through each word in the text
             */
            while (sc.hasNext()) {
                String word = sc.next();

                /**
                 *  Remove punctuation from the word
                 */
                word = word.replaceAll("[,\\.\\(\\)]", "");

                /** 
                 * If the word is not in the map, add it with a count of 1
                 */
                if (map.containsKey(word) == false) {
                    map.put(word, 1);
                 
                 /**
                  * If the word is already in the map, increment its count
                  */               
                } else {
                    int count = (int) (map.get(word));
                    map.remove(word);
                    map.put(word, count + 1);
                }
            }
            
            /**
             *  Convert the map to a list and sort it by count
             */            
            Set<Map.Entry<String, Integer>> set = map.entrySet();
            List<Map.Entry<String, Integer>> al = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(al, new Comparator<Map.Entry<String, Integer>>() {

                public int compare(Map.Entry<String, Integer> num, Map.Entry<String, Integer> words) {
                    return (words.getValue()).compareTo(num.getValue());
                }
            });

            /**
             * Display the top 20 words and their counts in a text area
             */     
            textArea.setText("");
            textArea.append(String.format("%-20s %10s%n", "Word", "Count"));
            textArea.append(String.format("%-20s %10s%n", "=========", "========="));

            int count = 0;
            for (Map.Entry<String, Integer> i : al) {
            	
            	/**
            	 * Remove any special characters from the word and display it with its count
            	 */  	 
            	textArea.append(String.format("%-20s %10s%n", i.getKey().replaceAll("[,\\.\\(\\)]", ""), i.getValue()));
                count++;

                /**
                 * Only display the top 20 words
                 */     
                if (count == 20) {
                    break;
                }
            }
            sc.close();
            
            /**
             * Display an error message if there is a problem reading the file  
             */ 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
    	/**
    	 * Create a new instance of the Main2 class
    	 */
        new Main2();
    }
}

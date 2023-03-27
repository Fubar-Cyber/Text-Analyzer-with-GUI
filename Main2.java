package TextAnalyzer;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.jsoup.*;
import org.jsoup.nodes.*;

@SuppressWarnings("serial")
public class Main2 extends JFrame implements ActionListener {

    private JTextField textField;
    private JTextArea textArea;

    public Main2() {
        super("UI Design Assignment for word occurrences");
        
        // Add color to GUI
        getContentPane().setBackground(Color.YELLOW);

        // Add features to panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        // Add features to label
        JLabel label = new JLabel("Please enter URL for website you wish to scan:");
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        label.setFont(labelFont);
        label.setForeground(Color.BLUE);
        
        // Add features to textField and textArea
        textField = new JTextField(10);
        textArea = new JTextArea(20, 10);
        textArea.setEditable(false);
        textArea.setForeground(Color.RED);
        Font font = new Font("Arial", Font.BOLD, 15);
        textArea.setFont(font);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        // Add features to button
        JButton button = new JButton("Capture first 20 words");
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        button.setFont(buttonFont);
        button.setForeground(Color.BLUE);
        button.addActionListener(this);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(button);

        // Add the components to the panel
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        panel.add(scrollPane);

        // Add the panel to the frame
        this.add(panel, BorderLayout.CENTER);

        // Set up the frame
        this.setPreferredSize(new Dimension(400, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String url = textField.getText();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter URL https://www.gutenberg.org/files/1065/1065-h/1065-h.htm or other in same format");
            return;
        }

        try {
            Document page = Jsoup.connect(url).get();
            Element div = page.select("div.chapter").first();
            String divText = div.text();

            Scanner sc = new Scanner(divText);
            Map<String, Integer> map = new HashMap<String, Integer>();

            while (sc.hasNext()) {
                String word = sc.next();
                word = word.replaceAll("[,\\.\\(\\)]", "");

                if (map.containsKey(word) == false) {
                    map.put(word, 1);
                } else {
                    int count = (int) (map.get(word));
                    map.remove(word);
                    map.put(word, count + 1);
                }
            }

            Set<Map.Entry<String, Integer>> set = map.entrySet();
            List<Map.Entry<String, Integer>> al = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(al, new Comparator<Map.Entry<String, Integer>>() {

                public int compare(Map.Entry<String, Integer> num, Map.Entry<String, Integer> words) {
                    return (words.getValue()).compareTo(num.getValue());
                }
            });

            textArea.setText("");
            textArea.append(String.format("%-20s %10s%n", "Word", "Count"));
            textArea.append(String.format("%-20s %10s%n", "=========", "========="));

            int count = 0;
            for (Map.Entry<String, Integer> i : al) {
            	textArea.append(String.format("%-20s %10s%n", i.getKey().replaceAll("[,\\.\\(\\)]", ""), i.getValue()));
                count++;

                if (count == 20) {
                    break;
                }
            }
            sc.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Main2();
    }
}

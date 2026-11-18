import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Top Five Destination List for SNHU Travel.
 * Fulfills User Story 1: displays an ordered list of the top five destinations
 * with name, short description, image, and package link text.
 * Customizations include a blue color scheme and name label.
 * Image credits: Unsplash (free to use under Unsplash License).
 */
public class TopFiveDestinationList {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TopDestinationListFrame frame = new TopDestinationListFrame();
            frame.setVisible(true);
        });
    }
}

class TopDestinationListFrame extends JFrame {

    private final DefaultListModel<TextAndIcon> listModel;

    public TopDestinationListFrame() {
        super("SNHU Travel - Top Five Destinations");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(850, 700);
        setLocationRelativeTo(null);

        // Color scheme customization: soft blue theme
        getContentPane().setBackground(new Color(230, 240, 255));

        listModel = new DefaultListModel<>();

        // Ordered list from most popular to fifth-most popular
        // Images loaded from resources folder (place images next to the class or in resources)
        addDestination("1. Paris, France",
                "Iconic city of lights with the Eiffel Tower and rich history. Top-selling package available.",
                "/paris.jpg");
        addDestination("2. Tokyo, Japan",
                "Vibrant blend of tradition and modern technology with world-class food. Top-selling package available.",
                "/tokyo.jpg");
        addDestination("3. Rome, Italy",
                "Eternal city featuring the Colosseum and ancient ruins. Top-selling package available.",
                "/rome.jpg");
        addDestination("4. New York City, USA",
                "Global hub of culture, Broadway, and iconic skyline. Top-selling package available.",
                "/newyork.jpg");
        addDestination("5. Sydney, Australia",
                "Stunning harbor city with the Opera House and beaches. Top-selling package available.",
                "/sydney.jpg");

        JList<TextAndIcon> list = new JList<>(listModel);
        list.setCellRenderer(new TextAndIconListCellRenderer(8, 8, 8, 8));
        list.setBackground(new Color(245, 248, 255));
        list.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Name label as required
        JLabel nameLabel = new JLabel("Developed by Megan Brackat", JLabel.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(new Color(30, 60, 120));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        add(scrollPane, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.SOUTH);
    }

    /**
     * Adds a destination with its description and image to the model.
     * Image path is relative to the classpath/resources.
     */
    private void addDestination(String name, String description, String imagePath) {
        // Combine name and description for display; image loaded separately
        String fullText = "<html><b>" + name + "</b><br>" + description + "</html>";
        ImageIcon icon = null;
        try {
            java.net.URL imgUrl = getClass().getResource(imagePath);
            if (imgUrl != null) {
                icon = new ImageIcon(imgUrl);
                // Scale image for list display
                java.awt.Image img = icon.getImage().getScaledInstance(120, 80, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("Could not load image: " + imagePath);
        }
        listModel.addElement(new TextAndIcon(fullText, icon));
    }
}

/**
 * Simple holder for text and icon used by the list renderer.
 */
class TextAndIcon {
    private final String text;
    private final Icon icon;

    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }
}

/**
 * Custom cell renderer that shows both an image and multi-line text.
 * Provides the additional visual customization required by the assignment.
 */
class TextAndIconListCellRenderer extends JPanel implements javax.swing.ListCellRenderer<TextAndIcon> {

    private final JLabel textLabel = new JLabel();
    private final JLabel iconLabel = new JLabel();

    public TextAndIconListCellRenderer(int top, int left, int bottom, int right) {
        setLayout(new BorderLayout(8, 0));
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        setOpaque(true);

        iconLabel.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 230), 1));
        add(iconLabel, BorderLayout.WEST);
        add(textLabel, BorderLayout.CENTER);
    }

    @Override
    public java.awt.Component getListCellRendererComponent(JList<? extends TextAndIcon> list,
                                                           TextAndIcon value,
                                                           int index,
                                                           boolean isSelected,
                                                           boolean cellHasFocus) {
        textLabel.setText(value.getText());
        iconLabel.setIcon(value.getIcon());

        if (isSelected) {
            setBackground(new Color(200, 220, 255));
            textLabel.setForeground(Color.BLACK);
        } else {
            setBackground(index % 2 == 0 ? new Color(245, 248, 255) : Color.WHITE);
            textLabel.setForeground(new Color(40, 40, 40));
        }
        return this;
    }
}

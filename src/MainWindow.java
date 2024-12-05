import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame {

    private JPanel lbl; // Container for thumbnails
    private JLabel lblTITLE;

    public MainWindow() {
        // Set up the main window frame
        setTitle("Select Animation");
        setSize(800, 600); // Adjust window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setBackground(Color.black);

        // Initialize components
        lbl = new JPanel();
        lbl.setLayout(new GridLayout(1, 3)); // 1 row, 3 columns for thumbnails

        lblTITLE = new JLabel("Select an Animation", SwingConstants.CENTER);
        lblTITLE.setFont(new Font("Arial", Font.BOLD, 24));

        // Create thumbnails
        createThumbnail("src/spearframes/thumb.png", "Spear Animation", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new spearframes(); // Open spearframes animation
            }
        });
        createThumbnail("src/kik/thumb.png", "Kik Animation", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new kik(); // Open kik animation
            }
        });
        createThumbnail("src/dekumyking/thumb.png", "Dekumykinh Animation", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new dekumykinh(); // Open dekumykinh animation
            }
        });

        // Layout setup
        setLayout(new BorderLayout());
        add(lblTITLE, BorderLayout.NORTH);
        add(lbl, BorderLayout.CENTER);

        setVisible(true); // Show the main window
    }

    // Create a thumbnail button with an image
    private void createThumbnail(String imagePath, String tooltip, ActionListener actionListener) {
        // Load image for thumbnail
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize image
        icon = new ImageIcon(image);

        // Create JButton with image as thumbnail
        JButton thumbnailButton = new JButton(icon);
        thumbnailButton.setToolTipText(tooltip); // Tooltip on hover
        thumbnailButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set black border with thickness 2
        thumbnailButton.setContentAreaFilled(false); // Transparent button background
        thumbnailButton.addActionListener(actionListener); // Add action listener to trigger animation

        // Add the thumbnail button to the panel
        lbl.add(thumbnailButton);
    }

    public static void main(String[] args) {
        new MainWindow(); // Launch the main window
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AnimationExample extends JFrame {
    private JPanel panel;
    private JLabel animationLabel;
    private Timer timer;
    private int frameIndex = 0;
    private String[] frames;

    public void generateFrames() {
        frames = new String[58]; // 58 frames in total
        for (int i = 0; i <= 57; i++) {
            frames[i] = "src/anim/spearframes/pixil-frame-" + i + ".png";
        }
    }

    public AnimationExample() {
        // Populate frames
        generateFrames();

        // Set up JFrame
        setTitle("Animation Example");
        setSize(800, 600); // Adjust window size
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up JPanel and JLabel for the animation
        panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Use BorderLayout for easy scaling
        animationLabel = new JLabel(); // Create an empty JLabel
        animationLabel.setHorizontalAlignment(JLabel.CENTER);
        animationLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(animationLabel, BorderLayout.CENTER); // Add JLabel to JPanel
        add(panel); // Add JPanel to JFrame

        // Create a Timer to update frames
        timer = new Timer(5, new ActionListener() { // 30ms per frame
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increment frame index and loop back if necessary
                frameIndex = (frameIndex + 1) % frames.length;

                // Load and scale the current frame
                String currentFrame = frames[frameIndex];
                if (isImageAvailable(currentFrame)) {
                    ImageIcon icon = new ImageIcon(currentFrame);
                    // Scale the image to fit the panel size
                    Image scaledImage = icon.getImage().getScaledInstance(
                            panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH
                    );
                    animationLabel.setIcon(new ImageIcon(scaledImage)); // Update JLabel's icon
                } else {
                    System.err.println("Warning: Image not found -> " + currentFrame);
                }
            }
        });

        // Add a listener to start the timer after the panel's size is initialized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (panel.getWidth() > 0 && panel.getHeight() > 0) {
                    timer.start(); // Start the animation only when panel size is valid
                    removeComponentListener(this); // Remove the listener after starting
                }
            }
        });

        setVisible(true);
    }

    /**
     * Checks if the image file exists.
     * @param imagePath The path to the image file.
     * @return true if the file exists, false otherwise.
     */
    private boolean isImageAvailable(String imagePath) {
        File file = new File(imagePath);
        return file.exists() && !file.isDirectory(); // Check if the file exists and is not a directory
    }

    public static void main(String[] args) {
        new AnimationExample();
    }
}

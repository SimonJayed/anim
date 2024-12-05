import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class AnimationExample extends JFrame {
    protected JPanel panel;
    protected JLabel animationLabel;
    protected Timer timer;
    protected int frameIndex = 0;
    protected String[] frames;

    /**
     * Loads all image files from the specified directory using FileReader.
     *
     * @param directoryPath The directory containing the frames.
     */
    public void loadFramesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Error: Directory does not exist or is not a directory -> " + directoryPath);
            frames = new String[0]; // Empty array to prevent errors
            return;
        }

        // Use FilenameFilter to get only image files (png, jpg, jpeg, gif)
        File[] files = directory.listFiles((FilenameFilter) (dir, name) ->
                name.matches(".*\\.(png|jpg|jpeg|gif)"));

        if (files != null && files.length > 0) {
            // Sort the files alphabetically to maintain frame order
            Arrays.sort(files);
            frames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                frames[i] = files[i].getAbsolutePath(); // Get the absolute path of each frame
            }
        } else {
            System.err.println("Error: No image files found in directory -> " + directoryPath);
            frames = new String[0];
        }
    }

    /**
     * Initializes the animation components.
     */
    public void initializeAnimation() {
        // Set up JPanel and JLabel for the animation
        panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Use BorderLayout for easy scaling
        animationLabel = new JLabel(); // Create an empty JLabel
        animationLabel.setHorizontalAlignment(JLabel.CENTER);
        animationLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(animationLabel, BorderLayout.CENTER); // Add JLabel to JPanel
        add(panel); // Add JPanel to JFrame

        // Create a Timer to update frames at regular intervals (30ms)
        timer = new Timer(30, e -> {
            if (frames == null || frames.length == 0) {
                System.err.println("No frames to animate.");
                timer.stop();
                return;
            }

            // Increment frame index and loop back if necessary
            frameIndex = (frameIndex + 1) % frames.length;

            // Load and scale the current frame
            String currentFrame = frames[frameIndex];
            ImageIcon icon = new ImageIcon(currentFrame);

            // Scale the image to fit the panel size
            Image scaledImage = icon.getImage().getScaledInstance(
                    panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH
            );
            animationLabel.setIcon(new ImageIcon(scaledImage)); // Update JLabel's icon
        });

        // Add a listener to start the timer after the panel's size is initialized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (panel.getWidth() > 0 && panel.getHeight() > 0 && frames.length > 0) {
                    // Start the animation after the panel size is valid
                    timer.start();
                    removeComponentListener(this); // Remove listener once started
                }
            }
        });
    }

    public static void main(String[] args) {
        AnimationExample animWorking = new AnimationExample();
        animWorking.loadFramesFromDirectory("src/spearframes"); // Set your directory path
        animWorking.setTitle("Animation Example");
        animWorking.setSize(800, 600); // Adjust window size
        animWorking.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        animWorking.setLocationRelativeTo(null);
        animWorking.initializeAnimation();
        animWorking.setVisible(true);
    }
}

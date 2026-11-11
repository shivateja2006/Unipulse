import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.ArrayList;

public class Main extends JFrame {
    private final FileManager fileManager = new FileManager();
    private final Color NAVY = new Color(24, 39, 75);
    private final Color BLUE = new Color(59, 91, 219);
    private final Color BG = new Color(245, 247, 252);
    private final Color TEXT = new Color(35, 43, 62);
    private final Color MUTED = new Color(117, 126, 148);

    private JPanel cardsPanel;
    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private JLabel totalValue, hackathonValue, internshipValue, workshopValue;
    private String selectedCategory = "All";

    public Main() {
        setTitle("UniPulse | Student Opportunity Hub");
        setSize(1180, 760);
        setMinimumSize(new Dimension(980, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        refreshDashboard();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBorder(new EmptyBorder(25, 18, 22, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("✦ UniPulse");
        logo.setFont(new Font("Arial", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("STUDENT OPPORTUNITY HUB");
        tagline.setFont(new Font("Arial", Font.BOLD, 9));
        tagline.setForeground(new Color(169, 184, 220));
        tagline.setBorder(new EmptyBorder(5, 2, 32, 0));
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(tagline);
        sidebar.add(sideLabel("MENU"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(navItem("▦   Dashboard", true));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("＋   Share Opportunity", false));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("⌕   Discover", false));
        sidebar.add(Box.createVerticalGlue());

        RoundedPanel note = new RoundedPanel(new Color(39, 57, 99), 18);
        note.setLayout(new BorderLayout(4, 8));
        note.setBorder(new EmptyBorder(14, 13, 14, 13));
        JLabel noteTitle = new JLabel("Grow together!");
        noteTitle.setForeground(Color.WHITE);
        noteTitle.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel noteText = new JLabel("<html><body style='width:145px'>Found a useful hackathon or internship? Share it with your campus community.</body></html>");
        noteText.setForeground(new Color(197, 207, 231));
        noteText.setFont(new Font("Arial", Font.PLAIN, 11));
        note.add(noteTitle, BorderLayout.NORTH);
        note.add(noteText, BorderLayout.CENTER);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(note);
        sidebar.add(Box.createVerticalStrut(15));

        JLabel version = new JLabel("UniPulse • Java Edition");
        version.setForeground(new Color(151, 166, 203));
        version.setFont(new Font("Arial", Font.PLAIN, 10));
        version.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(version);

        return sidebar;
    }

    private JLabel sideLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(139, 155, 193));
        label.setFont(new Font("Arial", Font.BOLD, 10));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel navItem(String text, boolean active) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(active ? Color.WHITE : new Color(190, 201, 225));
        label.setOpaque(true);
        label.setBackground(active ? new Color(59, 78, 130) : NAVY);
        label.setBorder(new EmptyBorder(12, 12, 12, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(190, 44));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (text.contains("Share")) {
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { showUploadDialog(); }
                public void mouseEntered(MouseEvent e) { label.setForeground(Color.WHITE); }
                public void mouseExited(MouseEvent e) { label.setForeground(new Color(190, 201, 225)); }
            });
        } else if (text.contains("Discover")) {
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    searchField.requestFocus();
                    searchField.setText("");
                    selectedCategory = "All";
                    categoryFilter.setSelectedItem("All");
                    refreshCards();
                }
            });
        }
        return label;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(25, 28, 24, 28));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel greeting = new JPanel();
        greeting.setOpaque(false);
        greeting.setLayout(new BoxLayout(greeting, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Discover opportunities");
        welcome.setFont(new Font("Arial", Font.BOLD, 27));
        welcome.setForeground(TEXT);

        JLabel sub = new JLabel("Find your next hackathon, internship or learning experience.");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(MUTED);
        sub.setBorder(new EmptyBorder(6, 1, 0, 0));

        greeting.add(welcome);
        greeting.add(sub);

        JButton upload = new JButton("+  Share an Opportunity");
        stylePrimaryButton(upload);
        upload.addActionListener(e -> showUploadDialog());

        top.add(greeting, BorderLayout.WEST);
        top.add(upload, BorderLayout.EAST);
        content.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 17));
        center.setOpaque(false);
        center.add(createStats(), BorderLayout.NORTH);
        center.add(createDiscoverArea(), BorderLayout.CENTER);
        content.add(center, BorderLayout.CENTER);

        return content;
    }

    private JPanel createStats() {
        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 0));
        stats.setOpaque(false);
        totalValue = addStatCard(stats, "TOTAL OPPORTUNITIES", "0", new Color(59, 91, 219));
        hackathonValue = addStatCard(stats, "HACKATHONS", "0", new Color(129, 86, 202));
        internshipValue = addStatCard(stats, "INTERNSHIPS", "0", new Color(27, 153, 125));
        workshopValue = addStatCard(stats, "WORKSHOPS", "0", new Color(224, 143, 54));
        return stats;
    }

    private JLabel addStatCard(JPanel parent, String heading, String value, Color accent) {
        RoundedPanel card = new RoundedPanel(Color.WHITE, 18);
        card.setLayout(new BorderLayout(4, 8));
        card.setBorder(new EmptyBorder(15, 16, 15, 16));

        JLabel number = new JLabel(value);
        number.setFont(new Font("Arial", Font.BOLD, 27));
        number.setForeground(accent);

        JLabel title = new JLabel(heading);
        title.setFont(new Font("Arial", Font.BOLD, 9));
        title.setForeground(MUTED);

        card.add(number, BorderLayout.CENTER);
        card.add(title, BorderLayout.SOUTH);
        parent.add(card);
        return number;
    }

    private JPanel createDiscoverArea() {
        JPanel area = new JPanel(new BorderLayout(0, 12));
        area.setOpaque(false);

        JPanel controls = new JPanel(new BorderLayout(10, 0));
        controls.setOpaque(false);

        JPanel searchWrap = new JPanel(new BorderLayout(8, 0));
        searchWrap.setBackground(Color.WHITE);
        searchWrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 232, 241)),
                new EmptyBorder(8, 12, 8, 12)));

        JLabel searchIcon = new JLabel("⌕");
        searchIcon.setFont(new Font("Arial", Font.BOLD, 20));
        searchIcon.setForeground(MUTED);

        searchField = new JTextField();
        searchField.setBorder(null);
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setToolTipText("Search opportunities...");
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { refreshCards(); }
        });

        searchWrap.add(searchIcon, BorderLayout.WEST);
        searchWrap.add(searchField, BorderLayout.CENTER);

        categoryFilter = new JComboBox<>(new String[]{
                "All", "Hackathon", "Internship", "Workshop",
                "Certification", "Competition", "Scholarship", "Other"});
        categoryFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryFilter.addActionListener(e -> {
            selectedCategory = categoryFilter.getSelectedItem().toString();
            refreshCards();
        });

        controls.add(searchWrap, BorderLayout.CENTER);
        controls.add(categoryFilter, BorderLayout.EAST);

        JLabel sectionTitle = new JLabel("Latest opportunities");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 17));
        sectionTitle.setForeground(TEXT);

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        titleRow.add(sectionTitle, BorderLayout.WEST);

        cardsPanel = new JPanel(new GridLayout(0, 2, 13, 13));
        cardsPanel.setOpaque(false);

        JPanel listWrap = new JPanel(new BorderLayout());
        listWrap.setOpaque(false);
        listWrap.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(listWrap);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel upper = new JPanel(new BorderLayout(0, 12));
        upper.setOpaque(false);
        upper.add(controls, BorderLayout.NORTH);
        upper.add(titleRow, BorderLayout.SOUTH);

        area.add(upper, BorderLayout.NORTH);
        area.add(scroll, BorderLayout.CENTER);
        return area;
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 17, 12, 17));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void showUploadDialog() {
        JTextField title = new JTextField(22);
        JComboBox<String> type = new JComboBox<>(new String[]{
                "Hackathon", "Internship", "Workshop", "Certification",
                "Competition", "Scholarship", "Other"});
        JTextField organizer = new JTextField(22);
        JTextField deadline = new JTextField(22);
        JTextField link = new JTextField(22);
        JTextField postedBy = new JTextField(22);
        JTextArea description = new JTextArea(4, 22);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        addFormRow(form, c, 0, "Opportunity title *", title);
        addFormRow(form, c, 1, "Category", type);
        addFormRow(form, c, 2, "Organizer", organizer);
        addFormRow(form, c, 3, "Deadline", deadline);
        addFormRow(form, c, 4, "Registration link", link);
        addFormRow(form, c, 5, "Your name *", postedBy);
        addFormRow(form, c, 6, "Short description", new JScrollPane(description));

        int result = JOptionPane.showConfirmDialog(this, form,
                "Share with the UniPulse Community", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (title.getText().trim().isEmpty() || postedBy.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the opportunity title and your name.");
                return;
            }

            Opportunity op = new Opportunity(title.getText().trim(),
                    type.getSelectedItem().toString(), organizer.getText().trim(),
                    deadline.getText().trim(), link.getText().trim(),
                    postedBy.getText().trim(), description.getText().trim());

            fileManager.saveOpportunity(op);
            refreshDashboard();
            refreshCards();
            JOptionPane.showMessageDialog(this,
                    "Opportunity shared successfully with UniPulse!");
        }
    }

    private void addFormRow(JPanel panel, GridBagConstraints c, int row,
                            String label, Component field) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(field, c);
    }

    private void refreshDashboard() {
        ArrayList<Opportunity> list = fileManager.loadOpportunities();
        totalValue.setText(String.valueOf(list.size()));
        hackathonValue.setText(String.valueOf(countType(list, "Hackathon")));
        internshipValue.setText(String.valueOf(countType(list, "Internship")));
        workshopValue.setText(String.valueOf(countType(list, "Workshop")));
        refreshCards();
    }

    private int countType(ArrayList<Opportunity> list, String type) {
        int count = 0;
        for (Opportunity op : list) {
            if (op.getType().equalsIgnoreCase(type)) count++;
        }
        return count;
    }

    private void refreshCards() {
        if (cardsPanel == null) return;

        cardsPanel.removeAll();
        ArrayList<Opportunity> list = fileManager.loadOpportunities();
        String keyword = searchField == null ? "" : searchField.getText().trim().toLowerCase();

        int shown = 0;
        for (Opportunity op : list) {
            boolean matchesSearch = op.getTitle().toLowerCase().contains(keyword)
                    || op.getOrganizer().toLowerCase().contains(keyword)
                    || op.getType().toLowerCase().contains(keyword)
                    || op.getDescription().toLowerCase().contains(keyword);

            boolean matchesCategory = selectedCategory.equals("All")
                    || op.getType().equalsIgnoreCase(selectedCategory);

            if (matchesSearch && matchesCategory) {
                cardsPanel.add(createOpportunityCard(op));
                shown++;
            }
        }

        if (shown == 0) {
            JPanel empty = new JPanel(new GridLayout(2, 1));
            empty.setOpaque(false);
            JLabel message = new JLabel("No opportunities found", SwingConstants.CENTER);
            message.setFont(new Font("Arial", Font.BOLD, 16));
            message.setForeground(TEXT);
            JLabel hint = new JLabel("Be the first to share an opportunity with your campus!", SwingConstants.CENTER);
            hint.setForeground(MUTED);
            empty.add(message);
            empty.add(hint);
            cardsPanel.setLayout(new BorderLayout());
            cardsPanel.add(empty, BorderLayout.CENTER);
        } else {
            cardsPanel.setLayout(new GridLayout(0, 2, 13, 13));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel createOpportunityCard(Opportunity op) {
        RoundedPanel card = new RoundedPanel(Color.WHITE, 18);
        card.setLayout(new BorderLayout(0, 9));
        card.setBorder(new EmptyBorder(15, 16, 14, 16));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel category = new JLabel("  " + op.getType().toUpperCase() + "  ");
        category.setOpaque(true);
        category.setBackground(new Color(235, 239, 255));
        category.setForeground(BLUE);
        category.setFont(new Font("Arial", Font.BOLD, 9));
        category.setBorder(new EmptyBorder(5, 6, 5, 6));

        JLabel date = new JLabel(op.getDeadline().isEmpty() ? "Deadline not added" : "Due: " + op.getDeadline());
        date.setFont(new Font("Arial", Font.PLAIN, 10));
        date.setForeground(MUTED);

        top.add(category, BorderLayout.WEST);
        top.add(date, BorderLayout.EAST);

        JLabel title = new JLabel("<html><div style='width:340px'>" +
                escapeHtml(op.getTitle()) + "</div></html>");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(TEXT);

        JLabel organizer = new JLabel(op.getOrganizer().isEmpty()
                ? "Organizer not specified" : op.getOrganizer());
        organizer.setFont(new Font("Arial", Font.PLAIN, 11));
        organizer.setForeground(MUTED);

        String descText = op.getDescription().isEmpty()
                ? "Explore this opportunity and check the registration details."
                : op.getDescription();
        JLabel description = new JLabel("<html><div style='width:340px'>" +
                escapeHtml(descText) + "</div></html>");
        description.setFont(new Font("Arial", Font.PLAIN, 11));
        description.setForeground(new Color(91, 101, 122));

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.add(title);
        details.add(Box.createVerticalStrut(4));
        details.add(organizer);
        details.add(Box.createVerticalStrut(8));
        details.add(description);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JLabel posted = new JLabel("Shared by " + op.getPostedBy());
        posted.setFont(new Font("Arial", Font.PLAIN, 10));
        posted.setForeground(MUTED);

        JButton open = new JButton("View Link  ↗");
        open.setFont(new Font("Arial", Font.BOLD, 10));
        open.setForeground(BLUE);
        open.setFocusPainted(false);
        open.setBorder(new EmptyBorder(7, 10, 7, 10));
        open.setBackground(new Color(239, 242, 255));
        open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        open.addActionListener(e -> openLink(op.getLink()));

        bottom.add(posted, BorderLayout.WEST);
        bottom.add(open, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private void openLink(String link) {
        if (link == null || link.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No registration link was provided.");
            return;
        }
        try {
            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                link = "https://" + link;
            }
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not open this link. Please check it.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

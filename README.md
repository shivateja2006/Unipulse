# UniPulse - Student Opportunity Hub

**Discover. Upload. Share. Grow.**

UniPulse is a Java Swing desktop application for students to discover and share hackathons, internships, workshops, certifications, competitions and scholarships.

## Features
- Modern dashboard with opportunity statistics
- Students can upload/share opportunities
- Add title, category, organizer, deadline, registration URL, student name and description
- Opportunity cards show category, deadline, organizer and contributor
- Search by title, organizer, category or description
- Filter by opportunity category
- Open registration link in the computer's default browser
- Save and load opportunities using a local text file

## Technologies and Java topics
- Java Swing: JFrame, JPanel, JLabel, JButton, JTextField, JComboBox, JOptionPane, JScrollPane
- Classes and objects: `Opportunity`
- Constructors: initialize opportunity data
- Encapsulation: private fields and getter methods
- ArrayList: keep the loaded opportunity records
- File handling: BufferedReader and BufferedWriter
- Exception handling: IOException and link-opening errors
- Loops and conditions: search, filter and dashboard counts

## Requirements
- JDK 8 or later
- IntelliJ IDEA, Eclipse, NetBeans or VS Code with Java support

## Run from terminal
Open a terminal in the project folder:

```bash
javac -d out src/*.java
java -cp out Main
```

Or open the folder in a Java IDE and run `Main.java`.

The application creates `data/opportunities.txt` after the first upload.

## Important limitation
This version stores records on the computer where it is running. It is a desktop prototype, not yet an internet-connected multi-user service. For students on different devices to share uploads, a hosted backend and shared database are required.

## Demo steps
1. Run the application.
2. Click **Share an Opportunity**.
3. Add a sample hackathon or internship.
4. Check that the new card appears.
5. Search by its name.
6. Filter by category.
7. Click **View Link** to open its registration page.

## Author
Shivateja Gouru  
B.Tech - Computer Science and Engineering

## License
MIT

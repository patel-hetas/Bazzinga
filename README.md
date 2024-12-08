# **CipherNet Browser**

CipherNet is a privacy-focused web browser built with Java and JavaFX, designed to provide a secure, user-friendly browsing experience.

Repository URl - https://github.com/patel-hetas/Bazzinga

---

## **Features**
- **Multi-tab Functionality**: Seamlessly browse multiple websites.
- **Ad Blocking and Tracker Prevention**: Blocks intrusive ads and trackers.
- **Bookmark Management**: Save and organize favorite URLs.
- **Encrypted History**: Ensures browsing history is private and secure.
- **Note-Taking Integration**: Includes a built-in notepad for quick notes.
- **JavaScript Control**: Enables or disables JavaScript dynamically.

---

## **Setup Instructions**

### **Prerequisites**
1. Install **Java JDK [20.0.1]**: [Download here](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html).
2. Ensure **JavaFX SDK [20.0.1]** is available (provided in the project root).

### **Running the Project**
1. Open the CipherNet project in an IDE (e.g., Eclipse).
2. Configure the build path:
   - Add all JARs from `javafx-sdk-20.0.1/lib` to the project's **ModulePath**.
3. Adjust run configurations:
   - Add the following to **VM Arguments**:
     ```text
     --add-modules javafx.controls,javafx.fxml,javafx.web -Dprism.order=sw
     ```
4. Ensure JavaFX modules are imported and linked.
5. Run the project as a Java Application.

---

## **Contact**
For questions or issues, please reach out to the development team.


package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javafx.geometry.Pos;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.util.Timer;
import java.util.TimerTask;

public class Event extends Schedule {
	
    private String location;
    private Date startDate;
    private Date endDate;
    private LocalDate eventDate;

    public Event(String title, Date startDate, Date endDate, String location, LocalDate eventDate) {
        setTitle(title);
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.eventDate = eventDate;
    }
    public LocalDate getEventDate() {
		return eventDate;
	}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        // Mengatur pengingat untuk event
        setReminder();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    private void setReminder() {
        LocalDateTime startDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime reminderDateTime = startDateTime.minusDays(1);

        TimerTask reminderTask = new TimerTask() {
            @Override
            public void run() {
                showReminder();
            }
        };

        Timer timer = new Timer();
        timer.schedule(reminderTask, Date.from(reminderDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void showReminder() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Reminder");
            alert.setHeaderText(null);
            alert.setContentText("Event '" + getTitle() + "' is tomorrow!");

            // Menyesuaikan dialog alert
            Label label = new Label("Reminder");
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
            VBox vbox = new VBox(label, new Label("Event: " + getTitle()));
            vbox.setAlignment(Pos.CENTER_LEFT);
            vbox.setSpacing(10);
            alert.getDialogPane().setContent(vbox);

            alert.showAndWait();
        });
    }


	
}
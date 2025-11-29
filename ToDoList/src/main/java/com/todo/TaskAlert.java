// package com.todo;

// import java.sql.Date;
// import java.time.LocalDate;
// import java.util.List;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.TimeUnit;

// import javax.sound.sampled.AudioInputStream;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;

// public class TaskAlert {
//     private TaskDAO taskDAO;
//     private int userId;

//     public TaskAlert(TaskDAO taskDAO, int userId) {
//         this.taskDAO = taskDAO;
//         this.userId = userId;
//     }

//     public void start() {
//         ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

//         scheduler.scheduleAtFixedRate(() -> {
//             List<Task> tasks = taskDAO.getPendingTasks(userId);
//             LocalDate today = LocalDate.now();

//             for (Task task : tasks) {
//                 Date dueDate = task.getDueDate();
//                 LocalDate due = dueDate.toLocalDate();

//                 if (due.equals(today)) {
//                     System.out.println("\n⚠️ ALERT! Task due today: " + task.getName());
//                     playSound("/alert.wav"); // Load from resources
//                 }
//             }

//         }, 0, 1, TimeUnit.MINUTES);
//     }

//     private void playSound(String resourcePath) {
//         try {
//             // Load audio from resources folder
//             AudioInputStream audioStream = AudioSystem.getAudioInputStream(
//                     getClass().getResourceAsStream(resourcePath)
//             );

//             Clip clip = AudioSystem.getClip();
//             clip.open(audioStream);
//             clip.start();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }


package com.todo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class TaskAlert {
    private TaskDAO taskDAO;
    private int userId;

    public TaskAlert(TaskDAO taskDAO, int userId) {
        this.taskDAO = taskDAO;
        this.userId = userId;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            List<Task> tasks = taskDAO.getPendingTasks(userId);
            LocalDate today = LocalDate.now();

            for (Task task : tasks) {
                Date dueDate = task.getDueDate();
                LocalDate due = dueDate.toLocalDate();

                if (due.equals(today)) {
                    System.out.println("\n⚠️ ALERT! Task due today: " + task.getName());
                    playSound("alert.wav"); // load from resources
                }
            }

        }, 0, 1, TimeUnit.MINUTES);
    }

    private void playSound(String soundFile) {
        try {
            // Load WAV from resources folder
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                getClass().getClassLoader().getResource(soundFile)
            );

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Release the file when done
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



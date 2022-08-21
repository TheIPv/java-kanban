import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static Gson gson = new Gson();

    private static TaskManager manager;

    public HttpTaskServer(TaskManager manager) throws IOException  {
            this.manager = manager;
            HttpServer httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/tasks", new TasksHandler()); // тут конфигурирование и запуск сервера
            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        //httpServer.stop(1);
    }

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException, IllegalArgumentException {
            String path = httpExchange.getRequestURI().getPath();
            if (path.split("/").length <= 2) {
                if (httpExchange.getRequestMethod().equals("GET")) {
                    System.out.println("Началась обработка запроса GET /tasks от клиента");
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(gson.toJson(manager.getPrioritizedTasks()).getBytes(DEFAULT_CHARSET));
                    }
                }
            } else if (path.split("/").length <= 4) {
                int taskId = -1;
                String toWrite = "";
                String taskResources = path.split("/")[2];
                try {
                    switch (taskResources) {
                        case "task":
                            if (httpExchange.getRequestURI().toString().contains("?")) {
                                taskId = Integer.valueOf(httpExchange.getRequestURI().toString().split("id=")[1]);
                                toWrite = gson.toJson(manager.getTaskById(taskId));
                            } else {
                                toWrite = gson.toJson(manager.getAllTasks());
                            }
                            break;
                        case "subtask":
                            if (httpExchange.getRequestURI().toString().contains("?")) {
                                if(httpExchange.getRequestURI().toString().contains("epic")) {
                                    System.out.println("Началась обработка запроса GET /tasks/subtask/epic/?id={epicId}");
                                    int epicId = Integer.valueOf(httpExchange.getRequestURI().toString().split("id=")[1]);
                                    try {
                                        toWrite = gson.toJson(manager.getEpicById(epicId).getSubtasks());
                                    } catch (NullPointerException nullPointerException) {
                                        httpExchange.sendResponseHeaders(404, 0);
                                        try (OutputStream os = httpExchange.getResponseBody()) {
                                        }
                                    }
                                    httpExchange.sendResponseHeaders(200, 0);
                                    try (OutputStream os = httpExchange.getResponseBody()) {
                                        os.write(toWrite.getBytes(DEFAULT_CHARSET));
                                    }
                                    return;
                                } else {
                                    taskId = Integer.valueOf(httpExchange.getRequestURI().toString().split("id=")[1]);
                                    toWrite = gson.toJson(manager.getSubtaskById(taskId));
                                }
                            }  else {
                                toWrite = gson.toJson(manager.getAllSubtasks());
                            }
                            break;
                        case "epic":
                            if (httpExchange.getRequestURI().toString().contains("?")) {
                                taskId = Integer.valueOf(httpExchange.getRequestURI().toString().split("id=")[1]);
                                toWrite = gson.toJson(manager.getEpicById(taskId));
                            } else {
                                toWrite = gson.toJson(manager.getAllEpics());
                            }
                            break;
                        case "history":
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(gson.toJson(manager.getHistory()).getBytes(DEFAULT_CHARSET));
                            }
                            return;
                        default:
                            httpExchange.sendResponseHeaders(404, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                            }
                    }
                } catch (NullPointerException nullPointerException) {
                    httpExchange.sendResponseHeaders(404, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                    }
                }
                if (httpExchange.getRequestMethod().equals("GET")) {
                    if (httpExchange.getRequestURI().toString().contains("?")) {
                        System.out.println("Началась обработка запроса GET /tasks/"+taskResources+"/?id={"+taskResources+"Id} от клиента");
                        try {
                            if (manager.getTaskById(taskId) != null) {
                                httpExchange.sendResponseHeaders(200, 0);
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    os.write(toWrite.getBytes(DEFAULT_CHARSET));
                                }
                            }
                        } catch (NullPointerException nullPointerException) {
                            httpExchange.sendResponseHeaders(404, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                            }
                        }
                    } else {
                        System.out.println("Началась обработка запроса GET /tasks/"+taskResources+" от клиента");
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(toWrite.getBytes(DEFAULT_CHARSET));
                        }
                    }
                } else if (httpExchange.getRequestMethod().equals("DELETE")) {
                    if(httpExchange.getRequestURI().toString().contains("?")) {
                        System.out.println("Началась обработка запроса DELETE /tasks/"+taskResources+"/?id={"+taskResources+"Id} от клиента");
                        try {
                            if (manager.getTaskById(taskId) != null) {
                                httpExchange.sendResponseHeaders(200, 0);
                                deleteRightType(taskId, taskResources);
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                }
                            }
                        } catch (NullPointerException nullPointerException) {
                            httpExchange.sendResponseHeaders(404, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                            }
                        }
                    } else {
                        System.out.println("Началась обработка запроса DELETE /tasks/"+taskResources+" от клиента");
                        deleteRightType(-1, taskResources);
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                        }
                    }
                } else if (httpExchange.getRequestMethod().equals("POST")) {
                    System.out.println("Началась обработка запроса POST /tasks/"+taskResources+" от клиента");
                    Headers requestHeaders = httpExchange.getRequestHeaders();
                    List<String> contentTypeValues = requestHeaders.get("Content-type");
                    if ((contentTypeValues != null) && (contentTypeValues.contains("application/json"))) {
                        httpExchange.sendResponseHeaders(201, 0);
                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        switch (taskResources) {
                            case "task":
                                Task task = gson.fromJson(body, Task.class);
                                try {
                                    manager.getTaskById(task.getId());
                                    manager.updateTask(task);
                                } catch (NullPointerException e) {
                                    manager.createTask(task);
                                }
                                break;
                            case "epic":
                                Epic epic = gson.fromJson(body, Epic.class);
                                try {
                                    manager.getEpicById(epic.getId());
                                    manager.updateEpic(epic);
                                } catch (NullPointerException e) {
                                    manager.createEpic(epic);
                                }
                                break;
                            case "subtask":
                                Subtask subtask = gson.fromJson(body, Subtask.class);
                                try {
                                    manager.getSubtaskById(subtask.getId());
                                    manager.updateSubtask(subtask);
                                } catch (NullPointerException e) {
                                    manager.createSubtask(subtask);
                                }
                                break;
                        }
                    } else {
                        httpExchange.sendResponseHeaders(400, 0);
                    }
                    try (OutputStream os = httpExchange.getResponseBody()) {
                    }
                }
            } else {
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                }
            }
        }
        private void deleteRightType(int taskId, String typeId) {
            switch(typeId) {
                case "epic":
                    if(taskId == -1) {
                        manager.deleteAllEpics();
                    } else {
                        manager.removeEpicById(taskId);
                    }
                    break;
                case "task":
                    if(taskId == -1) {
                        manager.deleteAllTasks();
                    } else {
                        manager.removeTaskById(taskId);
                    }
                    break;
                case "subtask":
                    if(taskId == -1) {
                        manager.deleteAllSubtasks();
                    } else {
                        manager.removeTaskById(taskId);
                    }
                    break;
            }
        }
    }
}

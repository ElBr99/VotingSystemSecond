package voting;

import voting.dto.TakeCandidateDto;
import voting.exception.AccessDenied;
import voting.exception.IncorrectLoginOrPassword;
import voting.model.Candidate;
import voting.repository.CandidateDao;
import voting.dto.CheckUserDto;
import voting.dto.CreateCandidateDto;
import voting.dto.CreateUserDto;
import voting.repository.UserDAO;
import voting.repository.VotersDAO;
import voting.repository.VotingDAO;
import voting.service.CandidateServiceImpl;
import voting.service.UserServiceImpl;
import voting.service.VotingServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


//Написать программу для проведения выборов. Избиратели должны предварительно зарегистрироваться в системе.
// Администратор заполняет список кандидатов. Каждый участник (по сети) или с того же самого компьютера входит в систему и голосует.
// Данные о проголосовавших накапливаются в базе данных.Дважды проголосовать нельзя.
// По окончании периода голосования администратор запускает процедуру подсчета голосов и система выдает результат.

//Основные алгоритмы^ Подведение итогов выборов.

public class AppRunner {
    private static final String EXIT = "exit";

    public static void main(String[] args) throws SQLException {
//        Class <Driver> driverClass = Driver.class;
//        try(var connection = ConnectionManager.get( );
//            var statement = connection.createStatement()) {
//            System.out.println(connection.getTransactionIsolation());
//            System.out.println(connection.getSchema());
//        };

// должен ли вызываться юзерсервис из мэйна или что-то другое должгл вызываться


        var userService = new UserServiceImpl(UserDAO.getInstance());
        var candidateService = new CandidateServiceImpl(CandidateDao.getInstance());
        var votingService = new VotingServiceImpl(VotersDAO.getInstance());


        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Добавить кандидатов");
            System.out.println("2. Зарегистрироваться");
            System.out.println("3. Войти");
            System.out.println("4. Проголосовать");
            System.out.println("5. Запустить процедуру подсчёта голосов");

            String lines = scanner.nextLine();

            String[] lineAr = lines.split("\\.");
            String str = lineAr[0];

            switch (str) {
                case "1":
                    while (true) {
                        System.out.println("Введите имя кандидата или введите exit для выхода");
                        Scanner scanner1 = new Scanner(System.in);
                        String name = scanner1.nextLine();
                        if (name.equalsIgnoreCase(EXIT)) {
                            break;
                        }
                        CreateCandidateDto createCandidateDto = new CreateCandidateDto(name);
                        try {
                            candidateService.addCandidates(createCandidateDto);
                        } catch (NullPointerException e) {
                            System.out.println("Не выполнен вход администратора");
                            break;
                        } catch (AccessDenied a) {
                            a.printStackTrace();
                            break;
                        }
                    }
                    break;
                case "2":
                    System.out.println("Введите логин");
                    Scanner scanner2 = new Scanner(System.in);
                    String login = scanner2.nextLine();
                    System.out.println("Введите имя");
                    String name2 = scanner2.nextLine();
                    System.out.println("Введите пароль");
                    String password = scanner2.nextLine();
                    CreateUserDto userDto = new CreateUserDto(name2, login, password);
                    userService.addUser(userDto);
                    break;

                case "3":
                    System.out.println("Введите логин");
                    Scanner scanner3 = new Scanner(System.in);
                    String login3 = scanner3.nextLine();
                    System.out.println("Введите пароль");
                    String password3 = scanner3.nextLine();
                    CheckUserDto checkUserDto = new CheckUserDto(login3, password3);
                    try {
                        userService.enter(checkUserDto);
                        break;
                    } catch (IncorrectLoginOrPassword e) {
                        e.printStackTrace();
                    }
                    break;

                case "4":
                    System.out.println("Выберите кандидата, за которого хотите проголосовать ");
                    List<TakeCandidateDto> helpList = candidateService.loadCandidates();
                    //  String string = helpList.stream().map(Candidate::toString).collect(Collectors.joining());

                    for (int i = 1; i < helpList.size(); i++) {
                        System.out.println(helpList.get(i));

                    }
                    Scanner scanner4 = new Scanner(System.in);
                    String candName = scanner4.nextLine();
                    Candidate candidate = new Candidate(candName);
                    votingService.vote(candidate);
                    // candidateService.addVoice(candidate);
                    System.out.println("Введите exit");
                    if (candName.equalsIgnoreCase(EXIT)) {
                        break;
                    }
                    break;

                case "5":
                    List<Candidate> helpList_5 = votingService.getResultVoices();
                    for (int i = 1; i < helpList_5.size(); i++) {
                        System.out.println(helpList_5.get(i));
                    }
                    break;
                    //   System.out.println(votingService.getResultVoices());
            }
        }
    }
}

package telnet;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
    Socket socket;

    public ServerProcess(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
        		BufferedReader netIn = new BufferedReader(
        			    new InputStreamReader(socket.getInputStream())
        			);

        			PrintWriter netOut = new PrintWriter(
        			    new OutputStreamWriter(socket.getOutputStream()), true
        			);
        ) {
            netOut.println("Hello client");

            String command;
            while (true) {
            	command = netIn.readLine();
            	System.out.println(command);
                if (command.equalsIgnoreCase("Exit")) {
                    netOut.println("Bye Client");
                    break;
                }

                StringTokenizer st = new StringTokenizer(command, "+-*/");
               
                String s1 = st.nextToken();
                String s2 = st.nextToken();
                char operator = command.charAt(s1.length());

                double op1, op2;
                try {
                    op1 = Double.parseDouble(s1);
                } catch (Exception e) {
                    netOut.println("Toán hạng 1 không phải số");
                    continue;
                }

                try {
                    op2 = Double.parseDouble(s2);
                } catch (Exception e) {
                    netOut.println("Toán hạng 2 không phải số");
                    continue;
                }

                double result;
                try {
                    result = calc(op1, op2, operator);
                } catch (Exception e) {
                    netOut.println(e.getMessage());
                    continue;
                }

                netOut.println(op1 +"" + operator+"" + op2 +" = "+ result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calc(double op1, double op2, char operator) {
        switch (operator) {
            case '+': return op1 + op2;
            case '-': return op1 - op2;
            case '*': return op1 * op2;
            case '/':
                if (op2 == 0) throw new IllegalArgumentException("Không thể chia cho 0");
                return op1 / op2;
            default:
                throw new IllegalArgumentException("Operator không hợp lệ: " + operator);
        }
    }
}

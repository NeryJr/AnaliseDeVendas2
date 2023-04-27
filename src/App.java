import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Sale> saleList = new ArrayList<>();

        System.out.print("Enter the path: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String readLine = br.readLine();

            while (readLine != null) {
                String[] fields = readLine.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);
                saleList.add(new Sale(month, year, seller, items, total));

                readLine = br.readLine();
            }

            Set<String> sellers = saleList.stream()
                        .map(s -> s.getSeller())
                        .collect(Collectors.toSet());

            System.out.println("Total de vendas por vendedor:");

            sellers.stream().forEach(seller -> {
                double sum = 0;
                
                sum = saleList.stream()
                .filter(s -> s.getSeller().equals(seller))
                .map(s -> s.getTotal())
                .reduce(0.0, (x, y) -> x + y);
                System.out.println(seller + " - R$ " + String.format("%.2f", sum));
            });
                        
            
        }
        catch (IOException IOE) {
            System.out.println("Error: " + path + " (O sistema não pode encontrar o arquivo especificado)");
        }

        sc.close();
    }
}

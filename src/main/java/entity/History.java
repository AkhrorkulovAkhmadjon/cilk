package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String forCard;
    private String toCard;
    private String data= toString(DateTimeFormatter.ofPattern("yyyy-MM-dd 'avg' HH:mm:ss"));
    private Double sum;
    private Double balance;
    private String toString(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public History(String title, String forCard, String toCard, Double sum, Double balance) {
        this.title = title;
        this.forCard = forCard;
        this.toCard = toCard;
        this.sum = sum;
        this.balance= balance;
    }
}

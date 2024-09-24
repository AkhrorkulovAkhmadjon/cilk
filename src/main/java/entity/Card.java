package entity;

import enums.CardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String cardName;
    private String cardNumber;
    private String cardData;
    private String passwordCard;
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
    private Double balance;
}

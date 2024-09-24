package entity;

import lombok.Data;

import java.util.UUID;
@Data
public class UserCard {
    private UUID user_id;
    private UUID card_id;
}

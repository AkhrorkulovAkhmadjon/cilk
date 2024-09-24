    package entity;

    import enums.Role;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.UUID;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID user_id;
        private String name;
        private String email;
        private String password;
        private String birthday;
        private String passportNumber;
        private String telNumber;
        private String address;
        private String code;
        @OneToMany(cascade ={ CascadeType.PERSIST, CascadeType.REMOVE })
        private List<Card> cards;
        @Enumerated(EnumType.STRING)
        private Role role;
        private Boolean status;
        private Boolean checkAccount=true;
    }

package main.api.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class MessageEditRequest {

    private String id;
    private String text;
}

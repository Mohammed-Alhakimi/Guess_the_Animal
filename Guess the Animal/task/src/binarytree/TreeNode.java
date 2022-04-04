package binarytree;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class TreeNode {
    @Getter
    public String data;

    @Getter
    public Type type;
    @Getter
    public TreeNode yes;
    @Getter
    public TreeNode no;

    public TreeNode(String data) {
        this.data = data;
    }

    public TreeNode(String data, Type type) {
        this.type = type;
        this.data = data;
    }
@JsonIgnore
    public boolean isQuestion() {
        return Type.QUESTION.equals(this.type);
    }

    public enum Type {
        ANSWER, QUESTION;
    }
}

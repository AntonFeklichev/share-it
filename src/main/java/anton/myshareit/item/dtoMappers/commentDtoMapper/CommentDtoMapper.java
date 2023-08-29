package anton.myshareit.item.dtoMappers.commentDtoMapper;

import anton.myshareit.item.dto.comment.CommentDto;
import anton.myshareit.item.dto.comment.CreateCommentDto;
import anton.myshareit.item.dtoMappers.ItemMapper;
import anton.myshareit.item.entity.Comment;
import anton.myshareit.user.dtoMappers.UserMapper;

public class CommentDtoMapper {

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .item(ItemMapper.toItemDto(comment.getItem()))
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }


    public static CommentDto toDtoWithoutItem(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }


    public static Comment toComment(CreateCommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.text())
                .build();
    }

}

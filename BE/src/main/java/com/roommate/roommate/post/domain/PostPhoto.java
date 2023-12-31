package com.roommate.roommate.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roommate.roommate.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostPhoto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="post_id")
    private Post post;

    //파일 경로 저장
    private String photoUrl;

    @Builder
    public PostPhoto(Post post, String photoUrl){
        this.post=post;
        this.photoUrl=photoUrl;
        this.setIsDeleted(false);

        post.getPostPhotos().add(this);
    }

    public void delete(){
        this.setIsDeleted(true);
    }
}

package com.roommate.roommate.post.domain;
import com.roommate.roommate.common.BaseEntity;
import com.roommate.roommate.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;
    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    @Enumerated(EnumType.STRING)
    private PostArea area;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    private int likeCount;
    private int viewCount;
    private int fee;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedPost> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostPhoto> postPhotos = new ArrayList<>();


    @Builder
    public Post(String title, String body, int fee, PostCategory category, PostArea area, User user){
        this.title=title;
        this.body=body;
        this.category=category;
        this.area=area;
        this.fee=fee;
        this.user=user;
        this.setIsDeleted(false);
    }
    public void savedLikeCount(boolean like){
        if(like==false) {
            this.likeCount++;
        }else if(like==true){
            this.likeCount--;
        }else if(this.likeCount<=0){
            this.likeCount=0;
        }
    }
    public void increaseViewCount() {
        this.viewCount++;
    }
    public void update(String area, String body, String title, int fee, String category){
        this.title= title;
        this.body= body;
        this.area=PostArea.valueOf(area);
        this.category=PostCategory.valueOf(category);
        this.fee=fee;
    }
    public void delete(){
        this.setIsDeleted(true);
    }
    public List<Comment> getComments(){
        int size = this.comments.size();
        if(size<1) return null;
        List<Comment> comments = new ArrayList<>(this.comments);
        comments.removeIf(comment -> comment.getIsDeleted()==true);
        Collections.reverse(comments);
        return comments;
    }
}

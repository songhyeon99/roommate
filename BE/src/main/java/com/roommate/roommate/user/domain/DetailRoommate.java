package com.roommate.roommate.user.domain;

import com.roommate.roommate.post.domain.PostArea;
import com.roommate.roommate.post.domain.PostCategory;
import com.roommate.roommate.user.domain.enums.Gender;
import com.roommate.roommate.user.domain.enums.LifeCycle;
import com.roommate.roommate.user.domain.enums.Pet;
import com.roommate.roommate.user.domain.enums.Smoking;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailRoommate {
    @Enumerated(EnumType.STRING)
    private LifeCycle lifeCycle;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    @Enumerated(EnumType.STRING)
    private Gender roommateGender;
    @Enumerated(EnumType.STRING)
    private Pet pet;
    private Integer fee;
    private String wishRoommate;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    @Enumerated(EnumType.STRING)
    private PostArea area;

    public void update(LifeCycle lifeCycle,
                       Smoking smoking,
                       Gender gender,
                       Pet pet,
                       Integer fee,
                       String wishRoommate,
                       PostCategory category,
                       PostArea area){
        this.lifeCycle=lifeCycle;
        this.smoking=smoking;
        this.roommateGender =gender;
        this.pet=pet;
        this.fee=fee;
        this.wishRoommate=wishRoommate;
        this.category=category;
        this.area=area;
    }



}

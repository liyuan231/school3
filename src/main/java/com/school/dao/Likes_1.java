package com.school.dao;

import com.school.dto.golden.ch_bothlike;
import com.school.model.Likes;

import java.util.List;

public interface Likes_1 {
    Likes select_both(ch_bothlike both_id);

    List<Likes> get_all_likes(Integer id);

    List<Likes> get_like_me(Integer id);
}

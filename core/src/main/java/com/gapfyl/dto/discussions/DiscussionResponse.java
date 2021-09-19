package com.gapfyl.dto.discussions;


import com.gapfyl.dto.common.Base;
import com.gapfyl.enums.common.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiscussionResponse extends Base {

    private String title;

    private String description;

    private CategoryType categoryType;

    private String categoryId;

    private List<String> tags;

    private List<DiscussionResponse> replies;
}

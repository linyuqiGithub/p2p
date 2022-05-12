package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;


@Setter@Getter
public class SystemDictionaryItemQueryObject extends QueryObject {
    private String keyword;
    private Long parentId = 1L;
}

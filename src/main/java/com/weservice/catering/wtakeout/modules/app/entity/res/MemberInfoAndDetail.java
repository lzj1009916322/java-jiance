package com.weservice.catering.wtakeout.modules.app.entity.res;

import com.weservice.catering.wtakeout.modules.app.entity.MemberFlowEntity;
import com.weservice.catering.wtakeout.modules.app.entity.MemberInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class MemberInfoAndDetail extends MemberInfoEntity {
    private List<MemberFlowEntity> recentRecords;
}

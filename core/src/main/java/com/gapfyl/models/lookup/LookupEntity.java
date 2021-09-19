package com.gapfyl.models.lookup;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.common.LookupFilterCode;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

@Data
@Document(collection = Collections.LOOKUPS)
@EqualsAndHashCode(callSuper = false)
public class LookupEntity extends BaseAuditEntity {

    @Indexed
    @Field("lookup_filter_code")
    private LookupFilterCode lookupFilterCode;

    @Indexed(unique = true)
    @Field("lookup_filter_key")
    private String lookupFilterKey;

    @Field("lookup_filter_value")
    private String lookupFilterValue;

    @Field("lookup_map_filter_key")
    private String lookupMapFilterKey;

    @Field("lookup_map_filter_value")
    private String lookupMapFilterValue;

    @Field("is_active")
    private boolean active = false;
}

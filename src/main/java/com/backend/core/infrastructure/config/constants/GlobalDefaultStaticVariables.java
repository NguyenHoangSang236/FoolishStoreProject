package com.backend.core.infrastructure.config.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalDefaultStaticVariables {
    public static final String shippingOrderDetailsByClientCodeUrl = "/v2/shipping-order/detail-by-client-code";

    public static final String shippingFeeUrl = "/v2/shipping-order/fee";

    public static final String availableServicesUrl = "/v2/shipping-order/available-services";

    public static final String provinceDataListUrl = "/master-data/province";

    public static final String districtDataListUrl = "/master-data/district";

    public static final String wardDataListUrl = "/master-data/ward?district_id";

    public static final String createOrderUrl = "/v2/shipping-order/create";

    public static final String defaultGgDriveIdUserAvatarImage = "1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R";
}

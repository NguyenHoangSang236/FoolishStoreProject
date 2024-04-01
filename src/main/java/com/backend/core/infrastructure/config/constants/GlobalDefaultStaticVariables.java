package com.backend.core.infrastructure.config.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalDefaultStaticVariables {
    public static final String SHIPPING_ORDER_DETAILS_BY_CLIENT_CODE_URL = "/v2/shipping-order/detail-by-client-code";

    public static final String SHIPPING_FEE_URL = "/v2/shipping-order/fee";

    public static final String AVAILABLE_GHN_SERVICES_URL = "/v2/shipping-order/available-services";

    public static final String PROVINCE_LIST_URL = "/master-data/province";

    public static final String DISTRICT_LIST_URL = "/master-data/district";

    public static final String WARD_LIST_URL = "/master-data/ward?district_id";

    public static final String CREATE_ORDER_URL = "/v2/shipping-order/create";

    public static final String DEFAULT_GOOGLE_DRIVE_USER_AVATAR_IMAGE = "1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R";

    public static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret.json";
    
    public static final String SERVICE_ACCOUNT_PRIVATE_JSON_KEY = "src/main/resources/service_account_private_key.json";

    public static final String APPLICATION_NAME = "FoolishFashionStore";

    public static final String SECRET_SIGNING_KEY = "bfe5592125967470d11815718f8af95ca5b1a4214926dfa028b290002ebcd158";

}

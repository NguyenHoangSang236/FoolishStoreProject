package com.backend.core.infrastructure.config.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstantValue {
    public static final String MAILCHIMP_API_KEY = "fa25aea365883f1a5613dccd5768e535-us18";

    public static final String MAILCHIMP_TRANSACTIONAL_API_KEY = "md-J4bwuTAMhA0YqRhuP1M7wQ";

    public static final String MAILCHIMP_AUDIENCE_ID = "8fd18 ef427";

    public static final String PROVINCE_LIST_URL = "/master-data/province";

    public static final String DISTRICT_LIST_URL = "/master-data/district";

    public static final String WARD_LIST_URL = "/master-data/ward?district_id";

    public static final String DEFAULT_GOOGLE_DRIVE_USER_AVATAR_IMAGE = "1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R";

    public static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret.json";

    public static final String SERVICE_ACCOUNT_PRIVATE_JSON_KEY = "src/main/resources/service_account_private_key.json";

    public static final String APPLICATION_NAME = "FoolishFashionStore";

    public static final String SECRET_SIGNING_KEY = "bfe5592125967470d11815718f8af95ca5b1a4214926dfa028b290002ebcd158";

    public static final String COMMENT_ENDPOINT = "/websocket";

    public static final String REQUEST_ID = "request_id";

    public static final String GHN_REQUEST_TOKEN = "10a16ebf-7fa0-11ee-8bfa-8a2dda8ec551";

    public static final String GHN_SHOP_ID = "190298";

    public static final String GHN_API_DOMAIN = "https://dev-online-gateway.ghn.vn/shiip/public-api";

    public static final String GHN_CREATE_ORDER_URL = "/v2/shipping-order/create";

    public static final String GHN_SHIPPING_FEE_URL = "/v2/shipping-order/fee";

    public static final String GHN_AVAILABLE_GHN_SERVICES_URL = "/v2/shipping-order/available-services";

    public static final String GHN_SHIPPING_ORDER_DETAILS_BY_CLIENT_CODE_URL = "/v2/shipping-order/detail-by-client-code";
}

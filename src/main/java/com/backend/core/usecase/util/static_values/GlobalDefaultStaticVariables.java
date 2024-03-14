package com.backend.core.usecase.util.static_values;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalDefaultStaticVariables {
//    @Autowired
//    ValueRenderUtils valueRenderUtils;
//
//    public byte[] defaultCustomerAvatar = valueRenderUtils.convertStringToByteArray("iVBORw0KGgoAAAANSUhEUgAAAIkAAACJCAMAAAAv+uv7AAAAYFBMVEX///9AQEIxMTOysrL39/f6+vr09PQ8PD7FxcZLS03Pz8+CgoI+Pj8yMjInJyrb29s2NjkgICPm5uZFRUdlZWZ5eXmJiYpbW1sUFBeoqKhQUFLt7e3V1dWVlZUsLC+/v7/lLYbmAAAC5UlEQVR4nO2aDXOiMBBAQ8gXEkk0KCdU/P//8kiod7ZKvGqyOnP7ZrRT0hkeu5uQhRKCIAiCIAiCIAiCIAiCIMjL4UaVZakMf7HGqnbaUkqtdvXqhTKrtrFaFx6tbdOuXuQhdhtZXCI3O/YKkW23Lr6z7rbwIkNlr0SKwlYDtAjX8obIlCENXbf769R8JmgPK7LaLIgUxQZ2BnW3cxPy00GKqKXchPwoQJM6alIDmqx1xESv4US2vyIiRfELbnlTTdSkgSuUcoyajCWYyYFGTegBzKS8YwIXk/epk/eZO+ROxcKJkPrW3uSMhVxj3+e+8z73YqIi+xPQkEyVsrSkUMgq8XC3sI914P2XcbfmT9UZaJFpeeuvE2RbQwTM6S9Dz3b0Ww9Id4ILBpIeLr6cRrnGnmWkXVeKcMZBYsK9CeckfMKXqvvR2nFsxr5W/hDzf8D/jGeCCSb49BU+05eY+nE+GFUeSmWGcISzMCq8MRO5+vXpermvA8I882/Tz/nKpzD4Y/PYPC5YLhP21yRcMic+JsKYj48Psx1mV+YHvYcPWa70TBLhFISHa560TLlvu945V/Vd39alEWFsHg+pHPKEJcxQf6E+HkQdO0qd01VRTTgppab93pct8VXk64TzTCYXbA9ytNfrvbRUHiAf5rBjNS51gXqsjmDPuEpJo90olTCb+20b9ZhdWoAUnYrYHvaMLU65RY6bewH5DMvmmNWD7xcr9Upl3Oe8Ke/ibehX6C6fSH23Vr9EJd+W9hBv/a4ZMz00iLZbt8nThInmJ6mZ0U2OHVy0F14iR49855nJEuk7Qt4vd8IxZJ96VTn9ZCW5hKZe9heaz38IiksrEnl3cY/E7zYemjgzaafP4H6+lpzRLuULuQen8EzSh6LHR2eOh6bcqfSPJ8cvKelExBNl4gsl3c3H6KdMdLrnTOrxOeyx6UpWNfQZEk4efiqf4fTi/01BEARBEARBEARBEARBkP+H31PZIhZGR0GvAAAAAElFTkSuQmCC");
//
//    public BlockingQueue<Integer> generateNewInvoiceIdQueue = new ArrayBlockingQueue<Integer>(100);

    public static final String shippingOrderDetailsByClientCodeUrl = "/v2/shipping-order/detail-by-client-code";

    public static final String shippingFeeUrl = "/v2/shipping-order/fee";

    public static final String availableServicesUrl = "/v2/shipping-order/available-services";

    public static final String provinceDataListUrl = "/master-data/province";

    public static final String districtDataListUrl = "/master-data/district";

    public static final String wardDataListUrl = "/master-data/ward?district_id";

    public static final String createOrderUrl = "/v2/shipping-order/create";
}

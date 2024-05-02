package com.backend.core.usecase.service;

import com.backend.core.infrastructure.config.constants.ConstantValue;
import com.ecwid.maleorang.MailchimpClient;
import com.ecwid.maleorang.method.v3_0.lists.CampaignDefaultsInfo;
import com.ecwid.maleorang.method.v3_0.lists.ContactInfo;
import com.ecwid.maleorang.method.v3_0.lists.EditListMethod;
import com.ecwid.maleorang.method.v3_0.lists.ListInfo;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    public void sendMail() {
        try {
            MailchimpClient client = new MailchimpClient(ConstantValue.MAILCHIMP_API_KEY);

            ContactInfo contactInfo = new ContactInfo();
            contactInfo.company = "Foolish Store";
            contactInfo.city = "Ho Chi Minh";
            contactInfo.address1 = "Ap 2, xa Dong Thanh, huyen Hoc Mon";
            contactInfo.country = "VietNam";
            contactInfo.zip = "00700";
            contactInfo.state = "Ho Chi Minh";

            CampaignDefaultsInfo campaignDefaultsInfo = new CampaignDefaultsInfo();
            campaignDefaultsInfo.from_name = "Nguyen Hoang Sang";
            campaignDefaultsInfo.from_email = "nguyenhoangsang236@gmail.com";
            campaignDefaultsInfo.language = "vi";
            campaignDefaultsInfo.subject = "";

            EditListMethod.Create editListMethod = new EditListMethod.Create();
            editListMethod.name = "Như";
            editListMethod.permission_reminder = "Như yêu Sang";
            editListMethod.email_type_option = false;
            editListMethod.visibility = "pub";
            editListMethod.contact = contactInfo;
            editListMethod.campaign_defaults = campaignDefaultsInfo;

            ListInfo listInfo = client.execute(editListMethod);
            System.out.println(listInfo.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

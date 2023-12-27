SET foreign_key_checks = 0;

truncate invoices_with_products;
truncate delivery;
truncate cart;
truncate invoice;
truncate notification;

update products_management set available_quantity = 20;
update products_management set sold_quantity = 0;
update products_management set one_star_quantity = 0;
update products_management set two_star_quantity = 0;
update products_management set three_star_quantity = 0;
update products_management set four_star_quantity = 0;
update products_management set five_star_quantity = 0;
update products_management set overall_rating = 0;

SET foreign_key_checks = 1;
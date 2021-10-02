INSERT INTO crust(name, name_ru, price) VALUES ('Thin',	'Тонкая', 1);
INSERT INTO crust(name, name_ru, price) VALUES ('Thick', 'Толстая', 2);
INSERT INTO crust(name, name_ru, price) VALUES ('Cheese filled', 'С сырным бортом', 3);

INSERT INTO cut(name, name_ru) VALUES ('Leave uncut', 'Оставить как есть');
INSERT INTO cut(name, name_ru) VALUES ('Cut into halves', 'Порезать пополам');
INSERT INTO cut(name, name_ru) VALUES ('Cut into 6 pieces', 'Порезать на 6 частей');
INSERT INTO cut(name, name_ru) VALUES ('Cut into 8 pieces', 'Порезать на 8 частей');
INSERT INTO cut(name, name_ru) VALUES ('Cut into squares', 'Порезать квадратами');

INSERT INTO size(name, name_ru, price, diameter) VALUES ('Small', 'Маленькая', 6, 18);
INSERT INTO size(name, name_ru, price, diameter) VALUES ('Medium', 'Средняя', 8, 24);
INSERT INTO size(name, name_ru, price, diameter) VALUES ('Large', 'Большая', 12, 30);
INSERT INTO size(name, name_ru, price, diameter) VALUES ('Family', 'Семейная', 15, 38);

INSERT INTO ingredient_type(id, name, name_ru, sort_order) VALUES (1, 'Cheeses', 'Сыры', 2);
INSERT INTO ingredient_type(id, name, name_ru, sort_order) VALUES (2, 'Veggies', 'Овощи', 1);
INSERT INTO ingredient_type(id, name, name_ru, sort_order) VALUES (3, 'Meats', 'Мясо', 3);
INSERT INTO ingredient_type(id, name, name_ru, sort_order) VALUES (4, 'Other', 'Другое', 5);
INSERT INTO ingredient_type(id, name, name_ru, sort_order) VALUES (5, 'Sauces', 'Соусы', 4);

INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://f12.pmo.ee/IpuA1Q4Wkkpc-ZWJGeVxAfF8Bsw=/685x0/smart/nginx/o/2016/02/08/5025461t1h1f7c.jpg', 'Pineapple', 'Ананас', 0.15, 4);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://i.ndtvimg.com/mt/cooks/2014-11/olives-zaitun.jpg', 'Olives', 'Оливки', 0.35, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://kiwi116.ru/wp-content/uploads/2019/02/34946538ec_500.jpg', 'Sweet onion', 'Сладкий лук', 0.10, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.mayrand.ca/globalassets/mayrand/catalog-mayrand/boucherie/52191-boeuf-hache-mi-maigre-congele-x-4-454-g.jpg?w=380&h=380&mode=crop', 'Ground beef', 'Говяжий фарш', 0.70, 3);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://freshmart.com.ua/storage/web/cache/product/1327/b0776dfb7a0121e720ac9289101f6579.jpeg?s=b9f51ddb184c7650d89c94fed7914f36', 'Jalapeño', 'Халапеньо', 0.35, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.melskitchencafe.com/wp-content/uploads/homemade-ricotta1.jpg', 'Ricotta', 'Рикотта', 0.40, 1);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://images.ua.prom.st/1743986285_tehnologiya-prigotovleniya-syra.jpg', 'Gauda', 'Гауда', 0.60, 1);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://thebakermama.com/wp-content/uploads/2018/08/fullsizeoutput_15a7c.jpg', 'Bacon', 'Бекон', 1.20, 3);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://bigoven-res.cloudinary.com/image/upload/d_recipe-no-image.jpg,t_recipe-480/sweet-sticky-and-spicy-chicken-38.jpg', 'Spicy chicken', 'Острая курица', 1, 3);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://countrystore.tabasco.com/media/catalog/product/cache/aedb4643a4e80e1d2c65abdb394bc06f/0/3/03009---original-red-60ml_pdp_wr_1.jpg', 'Tobasco sauce', 'Соус тобаско', 0.15, 5);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.littlebroken.com/wp-content/uploads/2020/02/Easy-Homemade-Basil-Pesto-15.jpg', 'Pesto sauce', 'Соус песто', 0.20, 5);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://cdn.shopify.com/s/files/1/0150/0232/products/Pearl_Valley_Sharp_Cheddar_Slices_grande.jpg?v=1524073010', 'Cheddar', 'Чеддар', 0.60, 1);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://myculturedpalate.com/wp-content/uploads/2010/01/Homemade-Mozzarella-Cheese-sliced-500x500.jpg', 'Mozzarella', 'Моцарелла', 0.50, 1);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://upload.wikimedia.org/wikipedia/commons/1/18/Salami_aka.jpg', 'Salami', 'Салями', 0.60, 3);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.budgetbytes.com/wp-content/uploads/2011/08/Thick-Rich-Pizza-Sauce-finished-500x500.jpg', 'Tomato sauce', 'Томатный соус', 0.20, 5);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://cdn.mos.cms.futurecdn.net/EBEXFvqez44hySrWqNs3CZ-320-80.jpg', 'Cucumber', 'Огурец', 0.10, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.gourmetsleuth.com/images/default-source/dictionary/capers-brined.jpg?sfvrsn=4', 'Capers', 'Каперсы', 0.25, 4);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.thespruceeats.com/thmb/1oVbAClKmbeEAO0HlcxTxv7ixRo=/1648x1236/smart/filters:no_upscale()/cream-sauce-in-copper-pan-18-57bbb3573df78c876360e2b5.jpg', 'Creamy sauce', 'Сметанный соус', 0.20, 5);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://www.welcometothetable.coop/sites/default/files/styles/amp_metadata_content_image_min_696px_wide/public/Portobello_Mushrooms.jpg?itok=rolbf9gr', 'Portobello', 'Шампиньон', 0.6, 4);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://cdn.shopify.com/s/files/1/0244/4961/3905/products/tomato_grande.jpg?v=1576807420', 'Tomato', 'Помидор', 0.15, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://featheremart.com/media/catalog/product/cache/12/image/600x600/9df78eab33525d08d6e5fb8d27136e95/5/6/5663.jpg', 'Garlic', 'Чеснок', 0.10, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://images-na.ssl-images-amazon.com/images/I/51lRpMsK6oL._AC_SX450_.jpg', 'Bell pepper', 'Сладкий перец', 0.20, 2);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://s3.amazonaws.com/finecooking.s3.tauntonclud.com/app/uploads/2017/04/19005342/fc46ze053-04-main.jpg', 'Feta', 'Фета', 0.50, 1);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://5.imimg.com/data5/GL/LT/MY-36282046/anchovies-cleaned-dressed-500x500.jpg', 'Anchovy', 'Анчоус', 0.35, 4);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://images.ua.prom.st/1252253281_w640_h640_zakvaska-dlya-syra.jpg', 'Parmesan', 'Пармезан', 1, 1);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://previews.123rf.com/images/utima/utima1604/utima160400068/55429151-tuna-canned-fish-isolated-on-white.jpg', 'Tuna', 'Тунец', 0.45, 3);
INSERT INTO ingredient(image_url, name, name_ru, price, ingredient_type_id) VALUES ('https://cafedelites.com/wp-content/uploads/2018/12/Honey-Baked-Ham-GLAZE-IMAGE-4-500x500.jpg', 'Ham', 'Ветчина', 0.80, 3);

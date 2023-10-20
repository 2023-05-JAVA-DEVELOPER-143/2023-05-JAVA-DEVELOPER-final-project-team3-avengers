insert into board_group (id,name) values(1,'자유게시판');
insert into board_group (id,name) values(2,'1:1문의');
insert into board_group (id,name) values(3,'FAQ');
insert into board_group (id,name) values(4,'공지사항');



insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (1, 'Rev', 'Hyderabad Airport', 4786, 6012, '2023-06-01', '2023-08-01', 1, 'Overhold', 'Veribet', 'Cardify', 'Bigtax', 'Treeflex', 1, 6544, 1);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (2, 'Mrs', 'Kalakaket Creek AS Airport', 5590, 4296, '2023-07-26', '2023-06-27', 2, 'Namfix', 'Namfix', 'Bitwolf', 'Alphazap', 'Domainer', 1, 4039, 3);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (3, 'Rev', 'Ouango Fitini Airport', 5993, 1057, '2023-05-20', '2023-07-16', 3, 'Cookley', 'Matsoft', 'Mat Lam Tam', 'Veribet', 'It', 2, 7738, 1);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (4, 'Ms', 'Turaif Domestic Airport', 6601, 1407, '2023-03-11', '2023-07-29', 4, 'Tresom', 'Fintone', 'Zontrax', 'Namfix', 'Zaam-Dox', 1, 6771, 1);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (5, 'Honorable', 'Grosseto Air Base', 6922, 8379, '2022-12-16', '2023-08-11', 5, 'Hatity', 'Redhold', 'Bigtax', 'Flexidy', 'Zathin', 2, 58, 2);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (6, 'Honorable', 'Big Spring Mc Mahon-Wrinkle Airport', 3668, 4569, '2023-03-23', '2023-02-24', 6, 'Veribet', 'Regrant', 'Tres-Zap', 'Pannier', 'Lotlux', 2, 5862, 3);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (7, 'Dr', 'Mörön Airport', 3331, 5665, '2023-05-13', '2023-10-10', 7, 'Bitwolf', 'Tampflex', 'Ronstring', 'Sub-Ex', 'Stringtough', 2, 7864, 2);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (8, 'Rev', 'Zamora Airport', 6439, 6270, '2023-04-15', '2023-08-31', 8, 'Hatity', 'Holdlamis', 'Toughjoyfax', 'Home Ing', 'Temp', 2, 1731, 1);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (9, 'Mr', 'Fairbanks International Airport', 2780, 2451, '2023-06-11', '2023-02-23', 9, 'Lotstring', 'Holdlamis', 'Daltfresh', 'Voltsillam', 'Bigtax', 1, 7993, 1);
insert into board (id, title, content, is_like, dis_like, create_time, update_time, member_id, img1, img2, img3, img4, img5, is_admin, read_count, board_group_id) values (10, 'Rev', 'Biangabip Airport', 721, 331, '2023-09-01', '2023-04-27', 10, 'Greenlam', 'Lotstring', 'Veribet', 'Namfix', 'Sub-Ex', 2, 8830, 2);




insert into like_config (id,board_id,member_id,status) values(1,1,1,1);
insert into like_config (id,board_id,member_id,status) values(2,2,2,2);
insert into like_config (id,board_id,member_id,status) values(3,3,3,1);


commit;
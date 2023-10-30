const API_BASE_URL="http://localhost:80";

const REMOVE_RECENT_VIEW={url:"/member/recentView/@optionSetId",method:"DELTE"};
const ADD_WISHLIST={url:"/member/wishlist/@optionSetId",method:"POST"};
const REMOVE_WISHLIST={url:"/member/wishlist/@optionSetId",method:"DELETE"};
const TAP_HEART={url:"product/heart/@optionSetId",method:"POST"};
const UNTAP_HEART={url:"/product/heart/@optionSetId",method:"DELETE"};
const CHILD_CATEGORY={url:"/product/category/@categoryId",method:"GET"};
const SHOW_OPTIONS={url:"/product/category/options/@categoryId",method:"GET"};
const SEARCH={url:"/product",method:"POST"};
const ADD_TO_CART={url:"/cart",method:"POST"};
const CLEAR_WISHLIST={url:"/wishlist", method:"DELETE"};
const CLEAR_RECENTVIEW={url:"/recentViews", method:"DELETE"};

export {API_BASE_URL,REMOVE_RECENT_VIEW,ADD_WISHLIST,REMOVE_WISHLIST,CLEAR_RECENTVIEW,
    TAP_HEART,UNTAP_HEART,CHILD_CATEGORY,SHOW_OPTIONS,SEARCH,ADD_TO_CART,CLEAR_WISHLIST}
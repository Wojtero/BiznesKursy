<?php
/* Smarty version 3.1.39, created on 2021-11-15 18:00:55
  from '/var/www/html/themes/classicPL/templates/catalog/_partials/product-additional-info.tpl' */

/* @var Smarty_Internal_Template $_smarty_tpl */
if ($_smarty_tpl->_decodeProperties($_smarty_tpl, array (
  'version' => '3.1.39',
  'unifunc' => 'content_61929247b6fe95_94437870',
  'has_nocache_code' => false,
  'file_dependency' => 
  array (
    'b43a8b5a98a8cc132ad737f054e11c216c59abd5' => 
    array (
      0 => '/var/www/html/themes/classicPL/templates/catalog/_partials/product-additional-info.tpl',
      1 => 1636911183,
      2 => 'file',
    ),
  ),
  'includes' => 
  array (
  ),
),false)) {
function content_61929247b6fe95_94437870 (Smarty_Internal_Template $_smarty_tpl) {
?><div class="product-additional-info js-product-additional-info">
  <?php echo call_user_func_array( $_smarty_tpl->smarty->registered_plugins[Smarty::PLUGIN_FUNCTION]['hook'][0], array( array('h'=>'displayProductAdditionalInfo','product'=>$_smarty_tpl->tpl_vars['product']->value),$_smarty_tpl ) );?>

</div>
<?php }
}

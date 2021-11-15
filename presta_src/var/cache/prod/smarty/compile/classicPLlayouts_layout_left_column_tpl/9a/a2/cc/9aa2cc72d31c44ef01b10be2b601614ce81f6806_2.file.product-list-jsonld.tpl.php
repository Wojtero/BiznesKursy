<?php
/* Smarty version 3.1.39, created on 2021-11-15 18:00:50
  from '/var/www/html/themes/classicPL/templates/_partials/microdata/product-list-jsonld.tpl' */

/* @var Smarty_Internal_Template $_smarty_tpl */
if ($_smarty_tpl->_decodeProperties($_smarty_tpl, array (
  'version' => '3.1.39',
  'unifunc' => 'content_6192924237d409_05124117',
  'has_nocache_code' => false,
  'file_dependency' => 
  array (
    '9aa2cc72d31c44ef01b10be2b601614ce81f6806' => 
    array (
      0 => '/var/www/html/themes/classicPL/templates/_partials/microdata/product-list-jsonld.tpl',
      1 => 1636911183,
      2 => 'file',
    ),
  ),
  'includes' => 
  array (
  ),
),false)) {
function content_6192924237d409_05124117 (Smarty_Internal_Template $_smarty_tpl) {
echo '<script'; ?>
 type="application/ld+json">
  {
    "@context": "https://schema.org",
    "@type": "ItemList",
    "itemListElement": [
        <?php
$_from = $_smarty_tpl->smarty->ext->_foreach->init($_smarty_tpl, $_smarty_tpl->tpl_vars['listing']->value['products'], 'item', false, 'position', 'productsForJsonLd', array (
  'last' => true,
  'iteration' => true,
  'total' => true,
));
$_smarty_tpl->tpl_vars['item']->do_else = true;
if ($_from !== null) foreach ($_from as $_smarty_tpl->tpl_vars['position']->value => $_smarty_tpl->tpl_vars['item']->value) {
$_smarty_tpl->tpl_vars['item']->do_else = false;
$_smarty_tpl->tpl_vars['__smarty_foreach_productsForJsonLd']->value['iteration']++;
$_smarty_tpl->tpl_vars['__smarty_foreach_productsForJsonLd']->value['last'] = $_smarty_tpl->tpl_vars['__smarty_foreach_productsForJsonLd']->value['iteration'] === $_smarty_tpl->tpl_vars['__smarty_foreach_productsForJsonLd']->value['total'];
?>
          {
            "@type": "ListItem",
            "position": <?php echo htmlspecialchars($_smarty_tpl->tpl_vars['position']->value, ENT_QUOTES, 'UTF-8');?>
,
            "name": "<?php echo htmlspecialchars($_smarty_tpl->tpl_vars['item']->value['name'], ENT_QUOTES, 'UTF-8');?>
",
            "url": "<?php echo htmlspecialchars($_smarty_tpl->tpl_vars['item']->value['url'], ENT_QUOTES, 'UTF-8');?>
"
            }<?php if (!(isset($_smarty_tpl->tpl_vars['__smarty_foreach_productsForJsonLd']->value['last']) ? $_smarty_tpl->tpl_vars['__smarty_foreach_productsForJsonLd']->value['last'] : null)) {?>,<?php }?>
          <?php
}
$_smarty_tpl->smarty->ext->_foreach->restore($_smarty_tpl, 1);?>]
        }
<?php echo '</script'; ?>
>
<?php }
}

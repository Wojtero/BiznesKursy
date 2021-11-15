<?php

use Symfony\Component\DependencyInjection\Argument\RewindableGenerator;

// This file has been auto-generated by the Symfony Dependency Injection Component for internal use.
// Returns the public 'prestashop.core.search.builder' shared service.

return $this->services['prestashop.core.search.builder'] = new \PrestaShop\PrestaShop\Core\Search\Builder\ChainedFiltersBuilder([0 => ${($_ = isset($this->services['prestashop.core.search.builder.typed']) ? $this->services['prestashop.core.search.builder.typed'] : $this->load('getPrestashop_Core_Search_Builder_TypedService.php')) && false ?: '_'}, 1 => ${($_ = isset($this->services['prestashop.core.search.builder.repository']) ? $this->services['prestashop.core.search.builder.repository'] : $this->load('getPrestashop_Core_Search_Builder_RepositoryService.php')) && false ?: '_'}, 2 => ${($_ = isset($this->services['prestashop.core.search.builder.request']) ? $this->services['prestashop.core.search.builder.request'] : ($this->services['prestashop.core.search.builder.request'] = new \PrestaShop\PrestaShop\Core\Search\Builder\RequestFiltersBuilder())) && false ?: '_'}, 3 => ${($_ = isset($this->services['prestashop.core.search.builder.event']) ? $this->services['prestashop.core.search.builder.event'] : $this->load('getPrestashop_Core_Search_Builder_EventService.php')) && false ?: '_'}, 4 => ${($_ = isset($this->services['prestashop.core.search.builder.persist']) ? $this->services['prestashop.core.search.builder.persist'] : $this->load('getPrestashop_Core_Search_Builder_PersistService.php')) && false ?: '_'}]);
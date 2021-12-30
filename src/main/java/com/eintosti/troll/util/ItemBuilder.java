package com.eintosti.troll.util;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author einTosti
 */
public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(XMaterial xMaterial) {
        this(xMaterial.parseItem());
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = Objects.requireNonNull(itemStack, "item");
        this.itemMeta = itemStack.getItemMeta();

        if (this.itemMeta == null) {
            throw new IllegalArgumentException("The type " + itemStack.getType() + " doesn't support item meta");
        }
    }

    public ItemBuilder type(XMaterial xMaterial) {
        Material material = Objects.requireNonNull(xMaterial.parseMaterial());
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment) {
        if (enchantment == null) {
            return this;
        }

        return enchant(enchantment, 1);
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchants() {
        this.itemMeta.getEnchants().keySet().forEach(this.itemMeta::removeEnchant);
        return this;
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(this.itemMeta);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        if (metaClass.isInstance(this.itemMeta)) {
            metaConsumer.accept(metaClass.cast(this.itemMeta));
        }
        return this;
    }

    public ItemBuilder name(String name) {
        this.itemMeta.setDisplayName(color(name));
        return this;
    }

    public ItemBuilder lore(String lore) {
        return lore(Collections.singletonList(lore));
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder lore(List<String> lore) {
        this.itemMeta.setLore(color(lore));
        return this;
    }

    public ItemBuilder addLore(String line) {
        List<String> lore = this.itemMeta.getLore();

        if (lore == null) {
            return lore(line);
        }

        lore.add(line);
        return lore(lore);
    }

    public ItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public ItemBuilder addLore(List<String> lines) {
        List<String> lore = this.itemMeta.getLore();

        if (lore == null) {
            return lore(lines);
        }

        lore.addAll(lines);
        return lore(lore);
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder flags() {
        return flags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.itemMeta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder skull(String skullOwner) {
        type(XMaterial.PLAYER_HEAD);
        meta(SkullMeta.class, skullMeta -> skullMeta.setOwner(skullOwner));
        return this;
    }

    public ItemBuilder armorColor(Color color) {
        return meta(LeatherArmorMeta.class, armorMeta -> armorMeta.setColor(color));
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

    private String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private List<String> color(List<String> input) {
        return input.stream()
                .map(this::color)
                .collect(Collectors.toList());
    }
}

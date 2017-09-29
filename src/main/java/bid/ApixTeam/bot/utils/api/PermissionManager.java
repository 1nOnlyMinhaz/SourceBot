package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class PermissionManager extends DatabaseManager {
    private static PermissionManager permissionManager = new PermissionManager();

    public static PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public boolean isMember(User user) {
        if (Lists.getUsers().contains(user.getIdLong()))
            return true;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `members` WHERE `UserID` = ?");
            ps.setLong(1, user.getIdLong());
            ResultSet rs = ps.executeQuery();
            boolean b = rs.next();
            closeConnection(connection, ps, rs);
            if (b)
                Lists.getUsers().add(user.getIdLong());
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createMember(User user) {
        if(isMember(user))
            return;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `members` (`UserID`, `Username`, `UserTag`, `AvatarUrl`, `AvatarId`) VALUES (?, ?, ?, ?, ?)");
            ps.setLong(1, user.getIdLong());
            ps.setString(2, user.getName());
            ps.setInt(3, Integer.parseInt(user.getDiscriminator()));
            ps.setString(4, user.getAvatarUrl());
            ps.setString(5, user.getAvatarId());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUserRank(User user, SimpleRank simpleRank){
        Lists.getUserPermissions().put(user.getIdLong(), simpleRank);
    }

    void setUserRank(long userId, SimpleRank simpleRank){
        Lists.getUserPermissions().put(userId, simpleRank);
    }

    public void setUserPermission(User user, SimpleRank simpleRank) {
        if (!Lists.getUsers().contains(user.getIdLong()))
            createMember(user);

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `members` SET `Permission` = ? WHERE `UserID` = ?");
            ps.setString(1, simpleRank.getDescription());
            ps.setLong(2, user.getIdLong());
            ps.executeUpdate();
            setUserRank(user, simpleRank);
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRoleRank(Role role, SimpleRank simpleRank){
        Lists.getRolePermissions().put(role.getIdLong(), simpleRank);
    }

    void setRoleRank(long roleId, SimpleRank simpleRank){
        Lists.getRolePermissions().put(roleId, simpleRank);
    }

    public void createRolePermission(BotAPI botAPI, Role role, SimpleRank simpleRank){
        if(Lists.getRolePermissions().containsKey(role.getIdLong())){
            setRolePermission(botAPI, role, simpleRank);
            return;
        }

        botAPI.getSettingsManager().addSetting(simpleRank.getSettings(), role.getId());
        setRoleRank(role, simpleRank);
    }

    public void setRolePermission(BotAPI botAPI, Role role, SimpleRank simpleRank){
        if(!Lists.getRolePermissions().containsKey(role.getIdLong())) {
            createRolePermission(botAPI, role, simpleRank);
            return;
        }

        botAPI.getSettingsManager().updateSetting(simpleRank.getSettings(), role.getId());
        setRoleRank(role, simpleRank);
    }

    public SimpleRank getUserPermission(User user){
        return Lists.getUserPermissions().get(user.getIdLong());
    }

    public SimpleRank getRolePermission(Role role){
        return Lists.getRolePermissions().get(role.getIdLong());
    }

    public List<Role> getUserRoles(Member member){
        return member.getRoles();
    }

    public boolean userRoleAtLeast(Member member, SimpleRank simpleRank) {
        List<Role> roles = getUserRoles(member);
        boolean b = false;
        for (Role role : roles) {
            if (!Lists.getRolePermissions().containsKey(role.getIdLong()))
                continue;

            if(b)
                break;

            b = getRolePermission(role).isAtLeast(simpleRank);
            System.out.println(b);
        }

        return b;
    }

    public boolean userRoleHigherThan(Member member, SimpleRank simpleRank) {
        List<Role> roles = getUserRoles(member);
        boolean b = false;
        for (Role role : roles) {
            if (!Lists.getRolePermissions().containsKey(role.getIdLong()))
                continue;

            if(b)
                break;

            b = getRolePermission(role).isHigherThan(simpleRank);
        }

        return b;
    }

    public boolean userRoleLowerThan(Member member, SimpleRank simpleRank) {
        List<Role> roles = getUserRoles(member);
        boolean b = false;
        for (Role role : roles) {
            if (!Lists.getRolePermissions().containsKey(role.getIdLong()))
                continue;

            if(b)
                break;

            b = getRolePermission(role).isLowerThan(simpleRank);
        }

        return b;
    }

    public boolean userAtLeast(User user, SimpleRank simpleRank) {
        SimpleRank rank = getUserPermission(user);
        return rank != null && rank.isAtLeast(simpleRank);
    }

    public boolean userHigherThan(User user, SimpleRank simpleRank) {
        SimpleRank rank = getUserPermission(user);
        return rank != null && rank.isHigherThan(simpleRank);
    }

    public boolean userLowerThan(User user, SimpleRank simpleRank) {
        SimpleRank rank = getUserPermission(user);
        return rank == null || rank.isLowerThan(simpleRank);
    }

    public boolean roleAtLeast(Role role, SimpleRank simpleRank) {
        SimpleRank rank = getRolePermission(role);
        return rank != null && rank.isAtLeast(simpleRank);
    }

    public boolean roleHigherThan(Role role, SimpleRank simpleRank) {
        SimpleRank rank = getRolePermission(role);
        return rank != null && rank.isHigherThan(simpleRank);
    }

    public boolean roleLowerThan(Role role, SimpleRank simpleRank) {
        SimpleRank rank = getRolePermission(role);
        return rank == null || rank.isLowerThan(simpleRank);
    }
}

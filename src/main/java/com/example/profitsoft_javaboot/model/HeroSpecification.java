package com.example.profitsoft_javaboot.model;

import org.springframework.data.jpa.domain.Specification;

public class HeroSpecification
{
    public static Specification<Hero> hasHeroTeamId(Long heroTeamId)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("heroTeam").get("heroTeamId"), heroTeamId);
    }

    public static Specification<Hero> hasHeroClassName(String heroClassName)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("heroClassName"), heroClassName);
    }

    public static Specification<Hero> hasHeroTeamName(String heroTeamName)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("heroTeam").get("heroTeamName"), heroTeamName);
    }
}

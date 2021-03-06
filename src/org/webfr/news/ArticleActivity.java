package org.webfr.news;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.os.Bundle;
import android.widget.TextView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;

public class ArticleActivity extends NetActivity {
	private Article article;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_detail);
		Log.i(News.LOGNAME, "web-fr.org lecteur de news : ouvre article");
		setArticle(News.article_ouvert);
		Button button = (Button) findViewById(R.id.b_article_ouvre);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i(News.LOGNAME, "ouvrir article");
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(News.article_ouvert.getUrlRedirect()));
				startActivity(i);
			}
		});
		button = (Button) findViewById(R.id.b_article_prec);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i(News.LOGNAME, "article précédent");
				if (News.position > 0) {
					News.position--;
					setArticle((Article)News.adapter.getItem(News.position));
				}
			}
		});
		button = (Button) findViewById(R.id.b_article_suiv);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i(News.LOGNAME, "article précédent");
				if (News.position+1 < News.adapter.getCount()) {
					News.position++;
					setArticle((Article)News.adapter.getItem(News.position));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		List<Groupe> groupes = News.getGroupes();
		for (int i=0;i<groupes.size();i++) {
			Groupe groupe = groupes.get(i);
			menu.add(Menu.NONE, new Integer(groupe.getId()), Menu.NONE, groupe.getNom());
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		this.article.association(item.getItemId());
		return true;
	}

	public void setArticle(Article article) {
		this.article = article;
		News.article_ouvert = article;
		TextView tv_titre = (TextView)findViewById(R.id.article_titre);
		TextView tv_desc = (TextView)findViewById(R.id.article_desc);
		TextView tv_site = (TextView)findViewById(R.id.article_site);
		tv_titre.setText(article.getTitre());
		tv_site.setText(article.getSite());
		if (testReseau())	
			tv_desc.setText(article.getDesc());
		else
			dlgSansReseau();
	}
}
